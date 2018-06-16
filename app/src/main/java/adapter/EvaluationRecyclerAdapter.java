package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import org.w3c.dom.Text;

import internetmodel.mycomment.UploadComment;
import internetmodel.product.Product;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EvaluationRecyclerAdapter extends RecyclerView.Adapter<EvaluationRecyclerAdapter.ViewHolder>{


    private static final String TAG = "EvaRecyclerAdapter";

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
            mRatingBar.setClickable(false);
            mRatingBar.setIsIndicator(true);
        }
    }

    private Context mContext;
    private UploadComment[] mComments;


    public EvaluationRecyclerAdapter(Context context,
                                     UploadComment[] comments){
        mContext = context;
        mComments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.evaluation_listitem,parent,false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null){
            holder.mUserName.setText(mComments[position].getRefereeNickName());
            holder.mComment.setText(mComments[position].getRefereeCommentContent());
            Log.i(TAG, mComments[position].toString());
            holder.mRatingBar.setProgress(Integer.valueOf(mComments[position].getRefereeRecommendLevel()));
            Glide.with(mContext).load(R.drawable.main_placeholder).into(holder.mUserImg);

        }
    }

    @Override
    public int getItemCount() {
        return mComments.length;
    }
}
