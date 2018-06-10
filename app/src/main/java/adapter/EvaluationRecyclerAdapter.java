package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import org.w3c.dom.Text;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EvaluationRecyclerAdapter extends RecyclerView.Adapter<EvaluationRecyclerAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mUserImg;
        private TextView mUserName;
        private TextView mRecommendText;
        private MaterialRatingBar mRatingBar;
        private TextView mComment;
        public ViewHolder(View itemView) {
            super(itemView);
            mUserImg = (ImageView) itemView.findViewById(R.id.product_evaluation_list_user_img);
            mUserName = (TextView) itemView.findViewById(R.id.product_evaluation_list_user_name);
            mRecommendText = (TextView) itemView.findViewById(R.id.product_evaluation_list_user_rate);
            mRatingBar = (MaterialRatingBar) itemView.findViewById(R.id.product_evaluation_list_user_star);
            mComment = (TextView) itemView.findViewById(R.id.product_evaluation_list_user_comment);
        }
    }

    private Context mContext;
    private String[] mUsername;
    private String[] mRateLevel;
    private String[] mComment;
    private String[] mLikeNum;
    private boolean[] mIsLike;

    public EvaluationRecyclerAdapter(Context context,
                                     String[] username,
                                     String[] rateLevel,
                                     String[] comment,
                                     String[] likeNum,
                                     boolean[] isLike) {
        mContext = context;
        mUsername = username;
        mRateLevel = rateLevel;
        mComment = comment;
        mLikeNum = likeNum;
        mIsLike = isLike;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.evaluation_listitem,parent,false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null){
            holder.mUserName.setText(mUsername[position]);
            holder.mComment.setText(mComment[position]);
            holder.mRatingBar.setProgress(Integer.valueOf(mRateLevel[position]));
            Glide.with(mContext).load(R.mipmap.ic_launcher).into(holder.mUserImg);
        }
    }

    @Override
    public int getItemCount() {
        return mUsername.length;
    }
}
