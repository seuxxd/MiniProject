package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seuxxd.miniproject.MainActivity;
import com.example.seuxxd.miniproject.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.ProductRecyclerAdapter;
import eventbusmodel.FirstLoginNotification;
import eventbusmodel.JumpToLookMeTab;
import eventbusmodel.UpdateProductAdapter;
import httpclient.RetrofitClient;
import internet.GetAllProducts;
import internet.GetRecommendByParams;
import internet.GetRecommendProducts;
import internetmodel.product.Product;
import internetmodel.product.ProductList;
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

    private ProductRecyclerAdapter mAdapter;


    private String mUsername;
    private ProgressDialog mDialog;

    private Product[] mAllProducts;

    private volatile int mFunctPosition = 0;
    private volatile int mClazzPosition = 0;
    private volatile int mPricePosition = 0;

    private static final String TAG = "RecommendFragment";
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
                        .setCancelable(false)
                        .setView(mDialogView)
                        .create();


        ImageView mImageCloseButton = (ImageView) mDialogView.findViewById(R.id.dialog_first_login_close);
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

        SharedPreferences sp = getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        sp.getString("username","empty");
        if (!MainActivity.isFirstLogin)
            initAdapterData();
        else if (MainActivity.isFirstLogin && MainActivity.POSITION == 0){
            EventBus.getDefault().post(new FirstLoginNotification());
        }
        return mView;
    }

    private void initAdapterData() {
        mDialog  = new ProgressDialog(getContext());
        mDialog.setMessage("数据拉取中，请等待");
        mUsername =
                getActivity()
                        .getSharedPreferences("user", Context.MODE_PRIVATE)
                        .getString("username","admin");
//        getRecommendProducts(mDialog, mUsername,"asc");
        Log.i(TAG, "initAdapterData: ");
        sortByServer();
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
        String[] mFunctionContent = {"不限","祛痘","祛斑","祛黑眼圈"};
        String[] mClassContent = {"不限","化妆/爽肤水","乳液/面霜","面膜/眼膜"};
        String[] mPriceContent = {"价格升序","价格降序"};
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
                mFunctPosition = position;
                sortByServer();
                Log.i(TAG, "onItemSelected: func");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClazzPosition = position;
                sortByServer();
                Log.i(TAG, "onItemSelected: class");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPriceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPricePosition = position;
                sortByServer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sortByServer() {
        SharedPreferences sp = getActivity().getSharedPreferences("skin",Context.MODE_PRIVATE);
        String cbre = sp.getString("dark_circle","10");
        String cp = sp.getString("acne","10");
        String cs = sp.getString("stain","10");
        Observable<Product[]> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetRecommendByParams.class)
                        .getProductsByParams(
                                cbre,
                                cp,
                                cs,
                                mUsername,
                                mPricePosition == 0 ? "asc" : "desc",
                                String.valueOf(mFunctPosition),
                                String.valueOf(mClazzPosition));
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Product[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Product[] products) {
                        Log.i(TAG, "onNext: " + Arrays.toString(products));
                        List<Product> mList = new ArrayList<>();
                        for (Product p : products)
                            mList.add(p);
                        ProductRecyclerAdapter mAdapter =
                                new ProductRecyclerAdapter(
                                        getContext(),
                                        mList,
                                        ((AppCompatActivity)getActivity()).getSupportFragmentManager(),
                                        false);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(
                                new LinearLayoutManager(
                                        getContext(),
                                        LinearLayoutManager.VERTICAL,
                                        false));
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

    public void sortProduct(String type , int position) {
        List<Product> productList = new ArrayList<>();
        String posi = String.valueOf(position);
        switch (type){
            case "func":
                for (Product p : mAllProducts){
                    if (p.getEffect().equals(posi))
                        productList.add(p);
                }
                break;
            case "clazz":
                for (Product p : mAllProducts){
                    if (p.getCategory().equals(posi))
                        productList.add(p);
                }

                break;
            default:
                break;
        }

        Product[] list = new Product[productList.size()];
        for (int i = 0 ; i < list.length ; i ++){
            list[i] = productList.get(i);
        }
        mAllProducts = list;
        ProductRecyclerAdapter adapter =
                new ProductRecyclerAdapter(
                        getContext(),
                        productList ,
                        ((AppCompatActivity)getActivity()).getSupportFragmentManager(),
                        false);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
