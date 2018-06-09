package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seuxxd.miniproject.R;

import java.util.List;

import fragment.BaseFragment;

/**
 * Created by SEUXXD on 2018/6/7.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {


    FragmentManager mFragmentManager ;
    Context mContext;
    List<BaseFragment> mFragmentList;
    String[] mTitle;

    public MainViewPagerAdapter(FragmentManager manager ,
                         Context context ,
                         List<BaseFragment> list ,
                         String[] title){
        super(manager);
        mFragmentManager = manager;
        mContext = context;
        mFragmentList = list;
        mTitle = title;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public View getCustomView(int position){
        View mView = LayoutInflater.from(mContext).inflate(R.layout.custom_tab,null);
        TextView mTextView = (TextView) mView.findViewById(R.id.tab_text);
        ImageView mImageView = (ImageView) mView.findViewById(R.id.tab_image);
        mTextView.setText(mTitle[position]);
        mImageView.setImageResource(R.mipmap.ic_launcher);
        return mView;
    }
}
