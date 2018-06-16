package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;

import httpclient.RetrofitClient;
import internet.UploadCommentService;
import internetmodel.mycomment.CommentResult;
import internetmodel.mycomment.UploadComment;
import internetmodel.product.Product;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class MyCommentActivity extends AppCompatActivity {


    private ImageView mBackImageView;
    private ImageView mProductImage;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductVolume;
    private MaterialRatingBar mRatingBar;
    private EditText mEditText;
    private Button mSubmitButton;

    private int productID;

    private Product mProduct;

    private static final String TAG = "MyCommentActivity";
    private String mUsername;

    private Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        Intent mIntent = getIntent();
        mBundle = mIntent.getExtras();
        productID = mIntent.getIntExtra("productID",0);
        mUsername = getSharedPreferences("user",MODE_PRIVATE).getString("username","empty");
        initView();
        initListener();
    }

    private void initListener() {
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserInput = mEditText.getText().toString();
                if (TextUtils.isEmpty(mUserInput))
                    return;
                float mRating = mRatingBar.getRating();
                UploadComment mComment = new UploadComment();
                mComment.setRefereeCommentContent(mUserInput);
                mComment.setRefereeNickName(mUsername);
                mComment.setRefereeRecommendLevel(String.valueOf((int)mRating));
                Observable<CommentResult> mObservable =
                        RetrofitClient
                                .getInstance()
                                .create(UploadCommentService.class)
                                .doUploadComment(mUsername,mProduct.getId(),String.valueOf((int)mRating),mUserInput);
                Log.i(TAG, "onClick: " + mUsername+mProduct.getId()+String.valueOf((int)mRating)+mUserInput);
                mObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CommentResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentResult commentResult) {
                        Log.i(TAG, "onNext: " + commentResult.getStatusCode());
                        if (commentResult.getStatusCode().equals("200")){
                            Toast.makeText(MyCommentActivity.this, "评论添加成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(MyCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(MyCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        });
        mRatingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                Toast.makeText(MyCommentActivity.this, "rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mBackImageView = (ImageView) findViewById(R.id.my_comment_back_button);
        mProductImage = (ImageView) findViewById(R.id.my_comment_image);
        mProductName = (TextView) findViewById(R.id.my_comment_product_name);
        mProductPrice = (TextView) findViewById(R.id.my_comment_product_price);
        mProductVolume = (TextView) findViewById(R.id.my_comment_product_volume);
        mRatingBar = (MaterialRatingBar) findViewById(R.id.my_comment_rating_bar);
        mEditText = (EditText) findViewById(R.id.my_comment_edit);
        mSubmitButton = (Button) findViewById(R.id.my_comment_button);
        mProduct = mBundle.getParcelable("product");
        Glide.with(this).load(mProduct.getImgUrl()).into(mProductImage);
        mProductName.setText(mProduct.getName());
        String price = "参考价： " + mProduct.getPrice();
        String volume = "净含量： "  + mProduct.getVolume();
        mProductPrice.setText(price);
        mProductVolume.setText(volume);
    }
}
