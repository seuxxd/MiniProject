package adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import dialog.EvaluationDialog;
import httpclient.RetrofitClient;
import internet.AddLikeProduct;
import internet.DeleteLikeProduct;
import internetmodel.product.Product;
import internetmodel.register.RegisterResult;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @ author leonxdxiao
 * @ date 2018/6/9
 * 商品展示的适配器
*/
public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private volatile Product[] mList;

    private Context mContext;

    private FragmentManager mManager;

    private String mUsername;

    private volatile boolean isLike;

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
                                  Product[] list,
                                  FragmentManager manager){
        super();
        mContext = context;
        mList = list;
        mManager = manager;
        mUsername =
                mContext
                        .getApplicationContext()
                        .getSharedPreferences("user",Context.MODE_PRIVATE)
                        .getString("username","empty");

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Product mProduct = mList[position];
        holder.mPosition.setText(String.valueOf(position + 1));
        Glide.with(mContext).load(mProduct.getImgUrl()).into(holder.mProductImage);
        holder.mProductName.setText(mProduct.getName());
        String priceShow = mContext.getResources().getString(R.string.product_price) + mProduct.getPrice();
        holder.mProductPrice.setText(priceShow);
        isLike = mProduct.getIsMeFavorite().equals("true");
        if (isLike)
            holder.mProductLike.setText("取消喜欢");
        else
            holder.mProductLike.setText("加入喜欢");
//        加入喜欢
        holder.mProductLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLike = mProduct.getIsMeFavorite().equals("true");
                if (!isLike)
                    addLikeProduct(mProduct,holder);
                else
                    deleteLikeProduct(mProduct,holder);
            }
        });
//        查看评论
        holder.mProductEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEvaluationDialog(mContext,mProduct,mManager);
            }
        });
    }

    private void addLikeProduct(final Product mProduct, final ViewHolder holder) {
        Observable<RegisterResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(AddLikeProduct.class)
                        .addLike(mUsername,mProduct.getId());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResult registerResult) {
                        switch (registerResult.getStatusCode()){
                            case "200":
                                Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
                                holder.mProductLike.setText("取消喜欢");
                                mProduct.setIsMeFavorite("true");
                                break;
                            default:
                                Toast.makeText(mContext, "添加失败！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "故障！请重试！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    /**
     *
     * @param context 展示对话框的上下文环境
     * @param product 哪个护肤品
     */
    private void showEvaluationDialog(Context context , Product product , FragmentManager manager){
//        View mContentView = LayoutInflater.from(context).inflate(R.layout.page_evaluation,null);
        EvaluationDialog mDialog = new EvaluationDialog();
        Bundle mBundle = new Bundle();
        mBundle.putParcelable("product",product);
        mDialog.setArguments(mBundle);
        mDialog.show(manager,null);
    }

    public FragmentManager getManager() {
        return mManager;
    }

    public void setManager(FragmentManager manager) {
        mManager = manager;
    }

    private void deleteLikeProduct(final Product product , final ViewHolder holder){
        Observable<RegisterResult> mObservable =
                RetrofitClient
                        .getInstance()
                        .create(DeleteLikeProduct.class)
                        .deleteProduct(mUsername,product.getId());
        mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterResult registerResult) {
                        switch (registerResult.getStatusCode()){
                            case "200":
                                Toast.makeText(mContext, "取消喜欢成功", Toast.LENGTH_SHORT).show();
                                product.setIsMeFavorite("false");
                                holder.mProductLike.setText("加入喜欢");
                                break;
                            case "404":
                            default:
                                Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
