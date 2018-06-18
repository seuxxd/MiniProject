package dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.MyCommentActivity;
import com.example.seuxxd.miniproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URI;
import java.net.URISyntaxException;

import adapter.EvaluationRecyclerAdapter;
import eventbusmodel.UpdateEvaluationAdapter;
import httpclient.RetrofitClient;
import internet.GetComment;
import internetmodel.mycomment.UploadComment;
import internetmodel.product.Product;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class EvaluationDialog extends DialogFragment {

    private Context mContext;
    private View mView;
    private int mGravity = Gravity.BOTTOM;
    private Dialog mDialog;
    private Bundle mBundle;

    RecyclerView mRecyclerView;

    private static final String TAG = "EvaluationDialog";

    private UploadComment[] mComments;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mBundle = getArguments();
    }


    @Subscribe
    public void setUpdateEvaluationAdapter(UpdateEvaluationAdapter updateEvaluationAdapter){
        if (updateEvaluationAdapter.getProduct() != null){
            updateEvaluation(updateEvaluationAdapter.getProduct(),mRecyclerView);
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View mContentView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.page_evaluation,null);

        ImageView mBackButton = (ImageView) mContentView.findViewById(R.id.product_evaluation_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDialog() != null)
                    getDialog().dismiss();
            }
        });

//        ImageView mShareButton = (ImageView) mContentView.findViewById(R.id.product_evaluation_share);
//        mShareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntent = new Intent(Intent.ACTION_SEND);
//                try {
//                    URI mURI = new URI("https://www.baidu.com");
//                    mIntent.putExtra(Intent.EXTRA_TEXT,"hello");
//                    mIntent.setType("*/*");
//                    mIntent.setPackage("com.tencent.mm");
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
//                startActivity(Intent.createChooser(mIntent,"请选择要分享的平台"));
//            }
//        });
        Button mLookMyCommentButton = (Button) mContentView.findViewById(R.id.look_my_comment);
        mLookMyCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), MyCommentActivity.class);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        Product mProduct = mBundle.getParcelable("product");
        if (mProduct == null){
            Toast.makeText(mContext, "数据获取错误，请重试！", Toast.LENGTH_SHORT).show();
        }
        else {
            ImageView mProductView = (ImageView) mContentView.findViewById(R.id.evaluation_product_img);
            TextView mNameText = (TextView) mContentView.findViewById(R.id.evaluation_product_name);
            TextView mPriceText = (TextView) mContentView.findViewById(R.id.evaluation_product_price);
            TextView mVolumeText = (TextView) mContentView.findViewById(R.id.evaluation_product_volume);
            mRecyclerView = (RecyclerView) mContentView.findViewById(R.id.product_evaluation_list);

            Glide.with(mContentView).load(mProduct.getImgUrl()).into(mProductView);
            mNameText.setText(mProduct.getName());
            String price = "参考价： " + mProduct.getPrice();
            String volume = "净含量： "  + mProduct.getVolume();
            mPriceText.setText(price);
            mVolumeText.setText(volume);

            updateEvaluation(mProduct, mRecyclerView);


        }


        mDialog = new AlertDialog
                .Builder(mContext)
                .setView(mContentView)
                .setCancelable(false)
                .create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return mDialog;
    }

    private void updateEvaluation(Product mProduct, final RecyclerView mRecyclerView) {
        Observable<UploadComment[]> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(GetComment.class)
                        .getComments(mProduct.getId());
        Log.i(TAG, "onCreateDialog: " + mProduct.getId());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadComment[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadComment[] comments) {
                        mComments = comments;
                        Log.i(TAG, "onNext: " + mComments);
                        EvaluationRecyclerAdapter mAdapter = new EvaluationRecyclerAdapter(getContext(),mComments);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Dialog_FullScreen);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void setContentView(View view){
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setGravity(int gravity){
        mGravity = gravity;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        Window mWindow = getDialog().getWindow();
//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams l = mWindow.getAttributes();
        l.width = ViewGroup.LayoutParams.MATCH_PARENT;
        l.height = ViewGroup.LayoutParams.MATCH_PARENT;
        l.gravity = mGravity;
        mWindow.setAttributes(l);
    }



}
