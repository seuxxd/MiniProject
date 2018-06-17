package personal.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seuxxd.miniproject.MainActivity;
import com.example.seuxxd.miniproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import base.fragment.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import commonmodels.SkinInfo;
import me.iwf.photopicker.PhotoPicker;
import personal.contact.LookView;
import report.activity.ReportActivity;
import utils.tools.FaceExtract;
import utils.view.MainLookUseDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.modules.personal.fragment
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public class MainLookSelfFragment extends BaseFragment implements LookView {

    public static File ImageFile = null;
    public static String ImagePath = null;

    AsyncTask<Void,Void,String> mTask;

    private static final String TAG = "MainLookSelfFragment";

    @BindView(R.id.headViewImg)
    ImageView headViewImg;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.useCameraBtn)
    Button useCameraBtn;    //拍照测肤
    @BindView(R.id.makeReportBtn)
    Button makeReportBtn;    //生成报告
    @BindView(R.id.generation_ReportLayout)
    LinearLayout generationReportLayout;
    @BindView(R.id.lookMainImg)
    ImageView lookMainImg;    //拍照后选择本地图片存取的照片

    private boolean mSelectPic = false;

    public static MainLookSelfFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_ID, id);
        MainLookSelfFragment fragment = new MainLookSelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutResouceId() {
        if (!mSelectPic)
            MainActivity.isFirstLogin = true;
        return R.layout.fragment_main_lookfor;
    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public void firstLazyLoad() {

    }

    @Override
    public void initView(View rootView) {

        //后期可以根据后台传递的参数进行设置
        showCenterPic("");
        showHeaderView("");

    }

    @Override
    public void doSomethingInCreate(Bundle arguments) {

    }


    @Override
    public void showDialog() {

        PhotoPicker.builder()
                .setPhotoCount(1)
                .setPreviewEnabled(true)
                .setShowGif(false)
                .setShowCamera(true)
                .start(getContext(), this, PhotoPicker.REQUEST_CODE);


    }

    @Override
    public void dissmisDialog() {

    }

    @Override
    public void showCenterPic(String path) {


        Glide.with(getContext()).load(path).apply(new RequestOptions().error(R.drawable.main_placeholder)).into(lookMainImg);



    }

    @Override
    public void showHeaderView(String path) {

        Glide.with(getContext()).load(path).into(headViewImg);

    }

    @Override
    public void showHowToUse() {
        MainLookUseDialog dialog = new MainLookUseDialog(getContext());
        dialog.setContent("这是一些测试的数据哦");
        dialog.show(getRootView());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                if (photos.size() > 0) {
                    //获取第一张照片
                    ImageFile = new File(photos.get(0));     //将照片封装成一个File
                    ImagePath = photos.get(0);       //照片的路径
                    showCenterPic(photos.get(0));
                    mSelectPic = true;
                    MainActivity.isFirstLogin = false;
                }
            }
        }
    }


    @OnClick({R.id.headViewImg, R.id.useCameraBtn, R.id.makeReportBtn, R.id.lookMainImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headViewImg:
                break;
            case R.id.useCameraBtn:
                showDialog();
                break;
            case R.id.makeReportBtn:
                if (mSelectPic) {
                  final ProgressDialog mDialog = new ProgressDialog(getActivity());
                    mDialog.setMessage("报告生成中，请稍等！");
                    mDialog.create();
                    mTask = new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                            if (mDialog != null && !mDialog.isShowing()){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.show();
                                    }
                                });
                            }
                            return FaceExtract.GetJsonByFaceApi(ImageFile);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            if (mDialog != null && mDialog.isShowing())
                                mDialog.dismiss();
                            Log.i(TAG, "onPostExecute: " + s);
                            try {
                                JSONObject mObject = new JSONObject(s);
                                JSONArray mArray = mObject.getJSONArray("faces");
                                if (mArray.length() == 0){
                                    Toast.makeText(getActivity(), "无法检测出人脸，请重新拍照！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else {
                                    JSONObject attribute = mArray.getJSONObject(0);
                                    Log.i(TAG, "onPostExecute: " + attribute);
                                    JSONObject skinstatus = attribute.getJSONObject("attributes").getJSONObject("skinstatus");
                                    Log.i(TAG, "onPostExecute: " + skinstatus.optString("dark_circle"));
                                    Log.i(TAG, "onPostExecute: " + skinstatus.optString("stain"));
                                    Log.i(TAG, "onPostExecute: " + skinstatus.optString("acne"));
                                    Log.i(TAG, "onPostExecute: " + skinstatus.optString("health"));
                                    SkinInfo mSkinInfo = new SkinInfo();
                                    mSkinInfo.setAcne(skinstatus.optString("acne"));
                                    mSkinInfo.setDark_circle(skinstatus.optString("dark_circle"));
                                    mSkinInfo.setStain(skinstatus.optString("stain"));
                                    mSkinInfo.setHealth(skinstatus.optString("health"));
                                    Intent mIntent = new Intent(getActivity(),ReportActivity.class);
                                    mIntent.putExtra("skin",mSkinInfo);
                                    SharedPreferences sp = getActivity().getSharedPreferences("skin",Context.MODE_PRIVATE);
                                    sp.edit()
                                            .putString("dark_circle",mSkinInfo.getDark_circle())
                                            .putString("stain",mSkinInfo.getStain())
                                            .putString("acne",mSkinInfo.getAcne())
                                            .putString("health",mSkinInfo.getHealth()).apply();
                                    startActivity(mIntent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        protected void onProgressUpdate(Void... values) {
                            super.onProgressUpdate(values);
                        }

                        @Override
                        protected void onCancelled() {
                            super.onCancelled();
                        }
                    };
                    mTask.execute();


                } else {
                    showToast(getString(R.string.please_select_pic));
                }
                break;
            case R.id.lookMainImg:
                break;
        }
    }
}



