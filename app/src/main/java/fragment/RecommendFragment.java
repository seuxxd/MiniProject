package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.seuxxd.miniproject.R;

import adapter.ProductRecyclerAdapter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_recommend,null,false);
        initView(mView);
//        初始化数据部分，通过网络获取
        String[] mUrl = {
                "http://101.132.154.189/test/1.jpg",
                "http://101.132.154.189/test/2.jpg",
                "http://101.132.154.189/test/3.jpg",
                "http://101.132.154.189/test/2.jpg",
                "http://101.132.154.189/test/1.jpg",
                "http://101.132.154.189/test/2.jpg",
                "http://101.132.154.189/test/3.jpg",
                "http://101.132.154.189/test/2.jpg"
        };
        String[] mName = {"abc","abc","abc","abc","abc","abc","abc","abc"};
        String[] mPrice = {"82","74","89","76","55","77","83","79"};
        ProductRecyclerAdapter mAdapter = new ProductRecyclerAdapter(
                getContext(),
                mUrl,
                mName,
                mPrice,
                ((AppCompatActivity)getActivity()).getSupportFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mView;
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
        String[] mFunctionContent = {"清痘","淡斑","美白","黑眼圈"};
        String[] mClassContent = {"爽肤水","乳液","面霜","眼霜","洁面","面膜"};
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
}
