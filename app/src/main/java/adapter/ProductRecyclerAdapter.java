package adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import java.util.List;

import dialog.EvaluationDialog;
import internetmodel.product.RecommendProduct;
import internetmodel.product.RecommendProductList;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 *
 * @ author leonxdxiao
 * @ date 2018/6/9
 * 商品展示的适配器
*/
public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private RecommendProductList mList;

    private Context mContext;

    private FragmentManager mManager;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mPosition;
        ImageView mProductImage;
        TextView mProductName;
        TextView mProductPrice;
        TextView mProductLike;
        TextView mProductEvaluation;

        public ViewHolder(View itemView) {
            super(itemView);
            mPosition = itemView.findViewById(R.id.product_id);
            mProductImage = itemView.findViewById(R.id.product_img);
            mProductName = itemView.findViewById(R.id.product_name);
            mProductPrice = itemView.findViewById(R.id.product_price);
            mProductLike = itemView.findViewById(R.id.product_like);
            mProductEvaluation = itemView.findViewById(R.id.product_evaluation);

        }


    }

    public ProductRecyclerAdapter(Context context,
                                  RecommendProductList list,
                                  FragmentManager manager){
        super();
        mContext = context;
        mList = list;
        mManager = manager;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.product_listitem,parent,false);
        CardView mCardView = (CardView) mView;
        mCardView.setContentPadding(30,10,10,30);
        mCardView.setRadius(20);
        return new ViewHolder(mCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RecommendProduct mProduct = mList.getProducts()[position];
        holder.mPosition.setText(String.valueOf(position + 1));
        Glide.with(mContext).load(mProduct.getImgUrl()).into(holder.mProductImage);
        holder.mProductName.setText(mProduct.getName());
        String priceShow = mContext.getResources().getString(R.string.product_price) + mProduct.getPrice();
        holder.mProductPrice.setText(priceShow);
        holder.mProductLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.mProductEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEvaluationDialog(mContext,mProduct.getId(),mManager);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.getProducts().length;
    }

    /**
     *
     * @param context 展示对话框的上下文环境
     * @param id 哪个护肤品
     */
    private void showEvaluationDialog(Context context , int id , FragmentManager manager){
//        View mContentView = LayoutInflater.from(context).inflate(R.layout.page_evaluation,null);
        EvaluationDialog mDialog = new EvaluationDialog();
        Bundle mBundle = new Bundle();
        mBundle.putInt("id",id);
        mDialog.setArguments(mBundle);
        mDialog.show(manager,null);
    }

    public FragmentManager getManager() {
        return mManager;
    }

    public void setManager(FragmentManager manager) {
        mManager = manager;
    }
}
