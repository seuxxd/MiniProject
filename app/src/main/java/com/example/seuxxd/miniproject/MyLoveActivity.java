package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import adapter.ProductRecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import internetmodel.product.Product;
import internetmodel.product.UserLikeProducts;

public class MyLoveActivity extends AppCompatActivity {



    private Unbinder mUnbinder = null;

    @BindView(R.id.my_love_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_love_back)
    ImageView mBackView;
    @OnClick(R.id.my_love_back)
    public void back(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love);
        if (mUnbinder == null)
            mUnbinder = ButterKnife.bind(this);
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        UserLikeProducts p = mBundle.getParcelable("products");
        if (p != null){
            Product[] products = p.getProducts();
            mRecyclerView.setAdapter(new ProductRecyclerAdapter(this,products,getSupportFragmentManager()));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
