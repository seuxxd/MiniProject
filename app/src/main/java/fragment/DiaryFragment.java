package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.MainActivity;
import com.example.seuxxd.miniproject.MyLoveActivity;
import com.example.seuxxd.miniproject.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import diary.fragment.MainDiaryFragment;
import diary.view.AddDiaryPopupwindow;
import httpclient.RetrofitClient;
import internet.AddDiaryService;
import internet.GetLikeProducts;
import internet.GetOneDiary;
import internet.GetSkinState;
import internetmodel.diary.DiaryInfo;
import internetmodel.product.Product;
import internetmodel.product.UserLikeProducts;
import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import utils.tools.DateUtils;

/**
 * Created by SEUXXD on 2018/6/7.
 */

public class DiaryFragment extends BaseFragment {

    private Unbinder unbinder = null;

    private String mUsername;

    private String mDate;

    private boolean isRunYear;
    private boolean isFebruary;

    private String[] datas;

    private static final String TAG = "DiaryFragment";

    AlertDialog mDialog = null;

    SharedPreferences sp;

    @BindView(R.id.jump_to_look_me)
    Button mJump;
    @OnClick(R.id.jump_to_look_me)
    public void jumpToLokMe(){
        ((MainActivity)getActivity()).getTabLayout().getTabAt(1).select();
    }

    @BindView(R.id.add_diary)
    com.getbase.floatingactionbutton.FloatingActionButton mAddDiary;
//    新增日记，时间肯定是今天
    @OnClick(R.id.add_diary)
    public void addDiary(){
        final AddDiaryPopupwindow mWindow = new AddDiaryPopupwindow(getContext());
        mWindow.setTime(new Date());
        //获取今天本地保存副本的内容
        mWindow.restoreInfo(sp);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.add_diary,null);
        mWindow.show(mView);
        mWindow.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: addDiary");
                mWindow.storeInfo(sp);
                uploadDiary(mWindow);
            }
        });
    }
//    查看日记，会有不同时间并且会点击上传日记功能
    @BindView(R.id.look_diary)
    com.getbase.floatingactionbutton.FloatingActionButton mLookDiaryButton;
    @OnClick(R.id.look_diary)
    public void lookDiary(){
        final View mDialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_choose_diary_date,null);
        final Spinner mSpinnerYear = (Spinner) mDialogView.findViewById(R.id.diary_choose_year);
        final Spinner mSpinnerMonth = (Spinner) mDialogView.findViewById(R.id.diary_choose_month);
        final Spinner mSpinnerDay = (Spinner) mDialogView.findViewById(R.id.diary_choose_day);


        String[] years = new String[50];
        final String[] months = new String[12];
        for (int i = 0 ; i < 50 ; i ++){
            years[i] = String.valueOf(2018 + i);
        }
        for (int i = 1 ; i <= 12 ; i ++)
            months[i - 1] = String.valueOf(i);


        initSpinner(mSpinnerYear, mSpinnerMonth, mSpinnerDay, years, months);



        ImageView mCloseView = (ImageView) mDialogView.findViewById(R.id.diary_choose_close);
        ImageView mSureView = (ImageView) mDialogView.findViewById(R.id.diary_choose_sure);
        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null)
                    mDialog.dismiss();
            }
        });
        mSureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: " +
                        mSpinnerYear.getSelectedItemPosition() +
                        mSpinnerMonth.getSelectedItemPosition() +
                        mSpinnerDay.getSelectedItemPosition());
                int month = mSpinnerMonth.getSelectedItemPosition() + 1;
                String temp1,temp2;
                if (month < 10)
                    temp1 = "0" + month;
                else
                    temp1 = "" + month;
                int day = mSpinnerDay.getSelectedItemPosition() + 1;
                if (day < 10)
                    temp2 = "0" + day;
                else
                    temp2 = "" + day;
                mDate = (mSpinnerYear.getSelectedItemPosition() + 2018) + "-" + temp1 + "-" + temp2;
                Log.i(TAG, "onClick: " + mDate);
                Log.i(TAG, "onClick: " + mUsername);
                Observable<DiaryInfo> mObservable =
                        RetrofitClient
                                .getInstance()
                                .create(GetOneDiary.class)
                                .getOneDiary(mUsername,mDate);
                mObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<DiaryInfo>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(DiaryInfo diaryInfo) {
                                Log.i(TAG, "onNext: " + diaryInfo);
                                final AddDiaryPopupwindow addDiaryPopupwindow = new AddDiaryPopupwindow(getContext());
                                View mView = LayoutInflater.from(getContext()).inflate(R.layout.add_diary,null);
                                addDiaryPopupwindow.setTime(diaryInfo.getDate());
                                addDiaryPopupwindow.setFoodEditText(diaryInfo.getFood() == null ? "" : diaryInfo.getFood());
                                addDiaryPopupwindow.setSportEditText(diaryInfo.getSport() == null ? "" : diaryInfo.getSport());
                                addDiaryPopupwindow.setOtherEditText(diaryInfo.getOthers() == null ? "" : diaryInfo.getOthers());
                                addDiaryPopupwindow.setMoodStar(Float.parseFloat(diaryInfo.getEmotion() == null ? "1" : diaryInfo.getEmotion()));
                                addDiaryPopupwindow.setSkinStar(Float.parseFloat(diaryInfo.getSkinState() == null ? "1" : diaryInfo.getSkinState()));
                                addDiaryPopupwindow.show(mView);
                                addDiaryPopupwindow.setSubmitListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        uploadDiary(addDiaryPopupwindow);
                                    }
                                });
                                if (mDialog.isShowing())
                                    mDialog.dismiss();
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
        });
        mDialog = new AlertDialog.Builder(getContext(),R.style.Dialog_FullScreen).setView(mDialogView).create();
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.show();
    }

    public void initSpinner(Spinner mSpinnerYear, final Spinner mSpinnerMonth, final Spinner mSpinnerDay, String[] years, final String[] months) {
        mSpinnerYear.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        years));
        mSpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = position + 2018;
                if (year % 4 == 0){
                    if ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0))
                        isRunYear = true;
                }
                mSpinnerMonth.setAdapter(
                        new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                months));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] days;
                switch (position){
                    case 1:
                        isFebruary = true;
                        if (isRunYear){
                            days = new String[29];
                            for (int i = 0 ; i < 29 ; i ++)
                                days[i] = String.valueOf(i + 1);
                        }
                        else{
                            days = new String[28];
                            for (int i = 0 ; i < 28 ; i ++)
                                days[i] = String.valueOf(i + 1);
                        }
                        break;
                    case 0:
                    case 2:
                    case 4:
                    case 6:
                    case 7:
                    case 9:
                    case 11:
                        days = new String[31];
                        for (int i = 0 ; i < 31 ; i ++)
                            days[i] = String.valueOf(i + 1);
                        break;
                    default:
                        days = new String[30];
                        for (int i = 0 ; i < 30 ; i ++)
                            days[i] = String.valueOf(i + 1);
                        break;
                }
                mSpinnerDay.setAdapter(
                        new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                days));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @BindView(R.id.my_love)
    com.getbase.floatingactionbutton.FloatingActionButton mLoveButton;
    @OnClick(R.id.my_love)
    public void loveButton(){
        Observable<Product[]> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetLikeProducts.class)
                        .getLikeProducts(mUsername);
        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Product[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Product[] products) {
                Log.i(TAG, "onNext: " + Arrays.toString(products));
                Intent mIntent = new Intent(getContext(), MyLoveActivity.class);
                Bundle mBundle = new Bundle();
                UserLikeProducts p = new UserLikeProducts();
                p.setProducts(products);
                mBundle.putParcelable("products",p);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
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

    @BindView(R.id.fab_menu)
    FloatingActionsMenu mFloatingActionsMenu;


    @BindView(R.id.chart_day)
    Spinner mDaySpinner;
    @BindView(R.id.chart_class)
    Spinner mClassSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View mView = inflater.inflate(R.layout.fragment_diary,null,false);
        unbinder = ButterKnife.bind(this,mView);



        mUsername =
                getContext()
                        .getSharedPreferences("user", Context.MODE_PRIVATE)
                        .getString("username","empty");
        final LineChart mChart = mView.findViewById(R.id.chart);
        CircleImageView mCircleView = mView.findViewById(R.id.user_img);
        Glide.with(getContext()).load(R.drawable.main_placeholder).into(mCircleView);
        sp = getContext().getSharedPreferences("diary",Context.MODE_PRIVATE);

        String[] mDays = {"选择时间","近7天","近30天","近90天"};
        String[] mClass = {"肌肤问题","痘痘","黑眼圈","色斑"};
        ArrayAdapter<String> mDayAdapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mDays);
        ArrayAdapter<String> mClassAdapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mClass);
        mDaySpinner.setAdapter(mDayAdapter);
        mDayAdapter.notifyDataSetChanged();
        mClassSpinner.setAdapter(mClassAdapter);
        mClassAdapter.notifyDataSetChanged();

        Log.i(TAG, "onCreateView: setSelection");
        mDaySpinner.setSelection(0,true);
        mClassSpinner.setSelection(0,true);
        mDaySpinner.invalidate();
        mClassSpinner.invalidate();
        mClassSpinner.setClickable(false);
        mClassSpinner.setEnabled(false);
        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    mClassSpinner.setSelection(0,true);
                    mClassSpinner.setEnabled(false);
                    return;
                }
                else {
                    mClassSpinner.setClickable(true);
                    mClassSpinner.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String days;
                String clazz;
                if (position == 0)
                    return;
                else {
                    int day = mDaySpinner.getSelectedItemPosition();
                    switch (day){
                        case 2:
                            days = "30";
                            break;
                        case 3:
                            days = "90";
                            break;
                        case 1:
                        default:
                            days = "7";
                            break;

                    }
                    switch (position){
                        case 2:
                            clazz = "cbre";
                            break;
                        case 3:
                            clazz = "cs";
                            break;
                        case 1:
                        default:
                            clazz = "cp";
                            break;

                    }
                    getUserSkinState(mUsername,days,mChart,clazz);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return mView;
    }

    @Override
    public void onDestroyView() {
        mDaySpinner.setSelection(0,true);
        Log.i(TAG, "onDestroyView: ");
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    /**
     * 上传日记功能
     */
    private void uploadDiary(final AddDiaryPopupwindow window){
        SharedPreferences sp = getActivity().getSharedPreferences("skin",Context.MODE_PRIVATE);
        String cbre = sp.getString("dark_circle","10");
        String cp = sp.getString("acne","10");
        String cs = sp.getString("stain","10");
        String skinState = sp.getString("health","10");
        String mFoodtext = window.getFoodEditText().trim();
        String mSporttext = window.getSportEditText().trim();
        String mOthertext = window.getOtherEditText().trim();
        if (TextUtils.isEmpty(mFoodtext) && TextUtils.isEmpty(mSporttext) && TextUtils.isEmpty(mOthertext)){
            Toast.makeText(getActivity(), "日记内容不能为空哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        Observable<RegisterResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(AddDiaryService.class)
                        .addDiary(
                                mUsername,
                                mFoodtext,
                                mSporttext,
                                mOthertext,
                                String.valueOf(window.getMoodStar()),
                                String.valueOf(window.getSkinStar()),
                                cbre,
                                cp,
                                cs,
                                skinState,
                                window.getDate());
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
                            case "200":
                                Toast.makeText(getContext(), "日记添加成功", Toast.LENGTH_SHORT).show();
                                window.dismiss();
                                break;
                            case "404":
                            default:
                                Toast.makeText(getContext(), "日记添加失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 获取用户皮肤信息并展示在图表中
     * @param username 用户名
     * @param days     天数
     * @param chart    图标
     * @param clazz    查询类别
     */
    private void getUserSkinState(String username , String days , final LineChart chart , final String clazz){


        Observable<DiaryInfo[]> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetSkinState.class)
                        .getSkinState(username,days);
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiaryInfo[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DiaryInfo[] diaryInfos) {
                        if (diaryInfos.length <= 0){
                            Toast.makeText(getActivity(), "还没有数据哦~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        datas = new String[diaryInfos.length];
                        for (DiaryInfo d : diaryInfos)
                            Log.i(TAG, "onNext: " + d);
                        XAxis x = chart.getXAxis();
                        x.setLabelCount(diaryInfos.length - 1);
                        for (int i = 0 ; i < diaryInfos.length ; i ++){
                            switch (clazz){
                                case "cp":
                                    datas[i] = diaryInfos[i].getCp();
                                    break;
                                case "cbre":
                                    datas[i] = diaryInfos[i].getCbre();
                                    break;
                                case "cs":
                                    datas[i] = diaryInfos[i].getCs();
                                    break;
                                case "skinState":
                                    datas[i] = diaryInfos[i].getSkinState();
                                    break;
                                default:
                                    datas[i] = "1";
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "出错，请退出重试！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        List<Entry> mDataList = new ArrayList<>();
                        if (datas == null || datas.length <= 0)
                            return;
                        for (int i = 0 ; i < datas.length ; i ++)
                            mDataList.add(new Entry(i,Float.valueOf(datas[i])));
                        LineDataSet mSet = new LineDataSet(mDataList,clazz);
                        mSet.setColor(Color.RED);
                        mSet.setValueTextColor(Color.BLUE);
                        chart.setData(new LineData(mSet));
                        chart.invalidate();
                    }
                });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
}
