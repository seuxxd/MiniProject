package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import de.hdodenhof.circleimageview.CircleImageView;
import httpclient.RetrofitClient;
import internet.RegisterService;
import internetmodel.register.RegisterResult;
import internetmodel.register.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import tencent.SingleTencent;


public class QQLoginFirstActivity extends AppCompatActivity {


    private CircleImageView mImageView;
    private EditText mNameEdit;
    private RadioButton mMaleRadio;
    private RadioButton mFemaleRadio;
    private Spinner mBirthSpinner;
    private Button mJumpButton;

    private boolean isSpinnerChecked = false;
    private int mSpinnerPosition = 0;

    private String mUsername = "empty";

    private static final String TAG = "QQLoginFirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin_success);
        Intent mIntent = getIntent();
        mUsername = mIntent.getStringExtra("openId");
        Log.i(TAG, "onCreate: " + mUsername);
        initView();
        mJumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegisterInfo(mUsername);
            }
        });
    }

    private void initView() {


        mImageView = (CircleImageView) findViewById(R.id.qq_login_img);
        mNameEdit = (EditText) findViewById(R.id.qq_login_name_edit);
        mMaleRadio = (RadioButton) findViewById(R.id.male);
        mFemaleRadio = (RadioButton) findViewById(R.id.female);
        mBirthSpinner = (Spinner) findViewById(R.id.qq_login_birth_spinner);
        mJumpButton = (Button) findViewById(R.id.qq_login_jump);


        Tencent mTencent = SingleTencent.getInstance(getApplicationContext());
        QQToken token = mTencent.getQQToken();
        Log.i(TAG, "initView: " + token);
        UserInfo mUserInfo = new UserInfo(QQLoginFirstActivity.this,token);
        mUserInfo.getOpenId(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i(TAG, "onComplete: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
        mUserInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Log.i(TAG, "onComplete: " + o.toString());
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
        Glide.with(this).load(R.drawable.main_placeholder).into(mImageView);
        mMaleRadio.setSelected(true);
        String[] mBirthYear = new String[100];
        for (int i = 99 ; i >= 0 ; i --)
            mBirthYear[99 - i] = String.valueOf(1900 + i);


        mBirthSpinner.setAdapter(
                new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.custom_spinner,
                        mBirthYear));


        mBirthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerPosition = position;
                isSpinnerChecked = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * 注册部分
     */
    private void sendRegisterInfo(String username){

        final User mUser = new User();

        String mNickName = mNameEdit.getText().toString();
        if (TextUtils.isEmpty(mNickName.trim())){
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mMaleRadio.isSelected() && !mFemaleRadio.isSelected()){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isSpinnerChecked){
            Toast.makeText(this, "请选择出生年份", Toast.LENGTH_SHORT).show();
            return;
        }


        if (username == null || TextUtils.isEmpty(username))
            username = "empty";
        mUser.setUsername(username);
        mUser.setNickname(mNickName);
        String mBirthday = String.valueOf(1999 - mBirthSpinner.getSelectedItemPosition()) + "-01-01";
        mUser.setBirthday(mBirthday);
        mUser.setPassword(String.valueOf(1));
        mUser.setSex(mMaleRadio.isSelected() ? "1" : "0");
        Log.i(TAG, "sendRegisterInfo: " + mUser);

        final Intent mIntent = new Intent(QQLoginFirstActivity.this,MainActivity.class);
        mIntent.putExtra("user",mUser);

        Observable<RegisterResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(RegisterService.class)
                        .firstLogin(
                                mUser.getUsername(),
                                mUser.getPassword(),
                                mUser.getNickname(),
                                mUser.getSex(),
                                mUser.getBirthday());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RegisterResult loginResult) {
                if (loginResult.getStatusCode().equals("200")){
                    Toast.makeText(QQLoginFirstActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(mIntent);
                    finish();
                }
                else {
                    Log.i(TAG, "onNext: " + loginResult.getStatusCode());
                    Toast.makeText(QQLoginFirstActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(QQLoginFirstActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
//                Log.i(TAG, "onComplete: " + o.toString());
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//    }
}
