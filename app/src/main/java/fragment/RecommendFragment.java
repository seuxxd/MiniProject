package fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.miniproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import adapter.ProductRecyclerAdapter;
import eventbusmodel.FirstLoginNotification;
import eventbusmodel.JumpToLookMeTab;
import eventbusmodel.UpdateProductAdapter;
import httpclient.RetrofitClient;
import internet.GetRecommendProducts;
import internetmodel.product.RecommendProductList;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by SEUXXD on 2018/6/7.
 */

public class RecommendFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private Toolbar mToolBar;
    private TextView mToolbarTitle;
    private Spinner mFunctionSpinner;
    private Spinner mClassSpinner;
    private Spinner mPriceSpinner;

    ProductRecyclerAdapter mAdapter;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(UpdateProductAdapter updateProductAdapter){
        /**
         * 获取可观察者模型，使用响应式编程，需要条件触发
         */
        initAdapterData();
    }

    @Subscribe
    public void firstLoginNotification(FirstLoginNotification notification){
        final View mDialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_first_login,null);

        final AlertDialog mDialog =
                new AlertDialog
                        .Builder(getContext())
                        .setView(mDialogView)
                        .create();


        ImageButton mImageCloseButton = (ImageButton) mDialogView.findViewById(R.id.dialog_first_login_close);
        Button mCloseButton = (Button) mDialogView.findViewById(R.id.dialog_first_login_close_button);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
                EventBus.getDefault().post(new JumpToLookMeTab());
            }
        };
        mImageCloseButton.setOnClickListener(listener);
        mCloseButton.setOnClickListener(listener);


        mDialog.show();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_recommend,null,false);
        initView(mView);
//        初始化数据部分，通过网络获取



        return mView;
    }

    private void initAdapterData() {
        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("数据拉取中，请等待");
        Observable<RecommendProductList> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetRecommendProducts.class)
                        .getProducts();
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RecommendProductList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //展示进度条，不可操作，直到完成下载部分
                        mDialog.show();
                    }

                    @Override
                    public void onNext(RecommendProductList recommendProductList) {
                        //在这里开始传递数据，展示列表
                        mAdapter = new ProductRecyclerAdapter(
                                getContext(),
                                recommendProductList,
                                ((AppCompatActivity)getActivity()).getSupportFragmentManager());
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //出错提示
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "出错，请重试", Toast.LENGTH_SHORT).show();
                                if (mDialog.isShowing())
                                    mDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onComplete() {
                        //完成后
                        if (mDialog.isShowing())
                            mDialog.dismiss();
                    }
                });
    }


    /* *
     *
     * @ author leonxdxiao
     * @ date 2018/6/9
     * 初始化界面，没有使用注入框架
     */

    private void initView(View mView) {


        mRecyclerView = (RecyclerView) mView.findViewById(R.id.product_recyclerView);
        mToolBar = (Toolbar) mView.findViewById(R.id.recommend_toolbar);
        mToolbarTitle = (TextView) mView.findViewById(R.id.recommend_toolbar_title);
        mFunctionSpinner = (Spinner) mView.findViewById(R.id.spinner_function);
        mClassSpinner = (Spinner) mView.findViewById(R.id.spinner_class);
        mPriceSpinner = (Spinner) mView.findViewById(R.id.spinner_price);


        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        mToolbarTitle.setText(R.string.recommend);
        /* *
         *
         * @ author leonxdxiao
         * @ date 2018/6/9
         * 这部分可以以后有时间再进行优化
         */
        initSpinnerContent();

        initSpinnerSelectedListener();
    }

    private void initSpinnerContent() {
        String[] mFunctionContent = {"不限","清痘","淡斑","美白","黑眼圈"};
        String[] mClassContent = {"不限","爽肤水","乳液","面霜","眼霜","洁面","面膜"};
        String[] mPriceContent = {"不限","价格升序","价格降序"};
        mFunctionSpinner.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mFunctionContent));
        mClassSpinner.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mClassContent));
        mPriceSpinner.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mPriceContent));
    }

    private void initSpinnerSelectedListener() {
        mFunctionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPriceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
