package base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.base.activity
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        mUnbinder = ButterKnife.bind(this);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    /**
     * 设置资源ID
     *
     * @return
     */
    public abstract int setLayoutResourceID();


    /**
     * 初始化一些组件
     */
    public abstract void initView();


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
