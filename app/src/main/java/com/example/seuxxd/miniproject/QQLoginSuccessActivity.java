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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import httpclient.RetrofitClient;
import internet.FirstLoginService;
import internetmodel.firstlogin.LoginResult;
import internetmodel.firstlogin.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class QQLoginSuccessActivity extends AppCompatActivity {


    private CircleImageView mImageView;
    private EditText mNameEdit;
    private RadioButton mMaleRadio;
    private RadioButton mFemaleRadio;
    private Spinner mBirthSpinner;
    private Button mJumpButton;

    private boolean isSpinnerChecked = false;
    private int mSpinnerPosition = 0;

    private static final String TAG = "QQLoginSuccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin_success);
        initView();
        mJumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegisterInfo();

                Intent mJumpToMainIntent = new Intent(QQLoginSuccessActivity.this,MainActivity.class);
                mJumpToMainIntent.putExtra("nickname","");

                startActivity(mJumpToMainIntent);
                finish();
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


        String[] mBirthYear = new String[100];
        for (int i = 0 ; i < 100 ; i ++)
            mBirthYear[i] = String.valueOf(1900 + i);


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
     * 登录
     */
    private void sendRegisterInfo(){

        User mUser = new User();

        String mNickName = mNameEdit.getText().toString();
        if (TextUtils.isEmpty(mNickName.trim())){
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mMaleRadio.isChecked() && !mFemaleRadio.isChecked()){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isSpinnerChecked){
            Toast.makeText(this, "请选择出生年份", Toast.LENGTH_SHORT).show();
            return;
        }


        mUser.setNickname(mNickName);
        mUser.setBirthday(String.valueOf(mBirthSpinner.getSelectedItemPosition() + 1900));
        mUser.setOpenID(String.valueOf(1));
        mUser.setSex(mMaleRadio.isSelected() ? "male" : "female");
        Log.i(TAG, "sendRegisterInfo: " + mUser);


        Observable<LoginResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(FirstLoginService.class)
                        .firstLogin(mUser);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginResult loginResult) {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(QQLoginSuccessActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });


    }
}
