package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URISyntaxException;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class MyCommentActivity extends AppCompatActivity {


    private ImageButton mBackimageButton;
    private ImageView mProductImage;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductVolume;
    private MaterialRatingBar mRatingBar;
    private EditText mEditText;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        initView();
        initListener();
    }

    private void initListener() {
        mBackimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

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
        mBackimageButton = (ImageButton) findViewById(R.id.my_comment_back_button);
        mProductImage = (ImageView) findViewById(R.id.my_comment_image);
        mProductName = (TextView) findViewById(R.id.my_comment_product_name);
        mProductPrice = (TextView) findViewById(R.id.my_comment_product_price);
        mProductVolume = (TextView) findViewById(R.id.my_comment_product_volume);
        mRatingBar = (MaterialRatingBar) findViewById(R.id.my_comment_rating_bar);
        mEditText = (EditText) findViewById(R.id.my_comment_edit);
        mSubmitButton = (Button) findViewById(R.id.my_comment_button);
    }
}
