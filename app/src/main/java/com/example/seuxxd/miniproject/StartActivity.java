package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


import org.json.JSONException;
import org.json.JSONObject;

import httpclient.RetrofitClient;
import internet.CheckHasUserService;
import internet.GetUserInfoById;
import internetmodel.login.LoginResult;
import internetmodel.register.RegisterResult;
import internetmodel.register.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qqlistener.BaseListener;
import tencent.SingleTencent;


public class StartActivity extends AppCompatActivity{

//    private TextView mUnLoginJumpTextView;
    private ImageButton mQQLoginButton;


    private static final String TAG = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(TAG, "onCreate: ");

        initView();

        initListener();

    }




    /**
     * 初始化所有的UI组件
     */
    private void initView(){

        mQQLoginButton = (ImageButton) findViewById(R.id.qq_login);

    }

    /**
     * 初始化事件监听器
     */
    private void initListener(){


        mQQLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    //QQ的登录，调用QQ登录SDK
    private void doLogin(){

        final Tencent mTencent = SingleTencent.getInstance(getApplicationContext());
        mTencent.login(StartActivity.this, "all", new IUiListener() {
            @Override
            public void onComplete(Object o) {
//                获取QQ登录信息
                JSONObject json = (JSONObject) o;
                final String openId;
                if (TextUtils.isEmpty(json.optString("openid")))
                    openId = "empty";
                else
                    openId = json.optString("openid");
                Log.i(TAG, "onComplete: openid " + openId);
//                根据QQ登录信息获取用户是否已经注册过
                getRegisterInfo(openId);
            }

            @Override
            public void onError(UiError uiError) {
                Log.i(TAG, "onError: " + uiError.errorCode);
                Log.i(TAG, "onError: " + uiError.errorDetail);
                Toast.makeText(StartActivity.this, "登陆错误！请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(StartActivity.this, "登录取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRegisterInfo(final String openId) {
        Observable<RegisterResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(CheckHasUserService.class)
                        .getHasUserInfo(openId);

        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResult registerResult) {
                        Log.i(TAG, "onNext: " + registerResult.getStatusCode());
                        switch (registerResult.getStatusCode()){

                            //用户存在，直接跳转到主页
                            case "200":
                                startMainActivity(openId);
                                break;
                            //用户不存在，跳转到注册界面
                            case "404":
                            default:
                                startRegisterActivity(openId);
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
        });
    }

    private void startRegisterActivity(String openId) {
        Toast.makeText(StartActivity.this, "用户还未注册！", Toast.LENGTH_SHORT).show();
        Intent mFirstIntent = new Intent(StartActivity.this,QQLoginFirstActivity.class);
        mFirstIntent.putExtra("openId",openId);
        startActivity(mFirstIntent);
        finish();
    }

    private void startMainActivity(final String openId) {
        Log.i(TAG, "startMainActivity: into startMainActivity");
        final Intent mIntent = new Intent(StartActivity.this,MainActivity.class);
        Observable<LoginResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetUserInfoById.class)
                        .getInfo(openId);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResult s) {
                        Log.i(TAG, "onNext: s" + s);
                        if (!TextUtils.isEmpty(s.getStatusCode()) && s.getStatusCode().equals("404"))
                            return;
                        else {
                            User mUser = new User();
                            mUser.setUsername(openId);
                            mUser.setNickname(s.getNickname());
                            mUser.setBirthday(s.getBirthday().substring(0,4));
                            mUser.setSex(s.getSex());
                            mUser.setPassword("1");
                            mIntent.putExtra("user",mUser);
                            startActivity(mIntent);
                            finish();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
        });
    }


    //    用于接受QQ登录回调信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,new BaseListener());
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseListener());
            }
        }
    }



}
