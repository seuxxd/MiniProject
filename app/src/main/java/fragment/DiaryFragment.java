package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.seuxxd.miniproject.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEUXXD on 2018/6/7.
 */

public class DiaryFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_diary,null,false);
        LineChart mChart = mView.findViewById(R.id.chart);
        List<Entry> mDataList = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i ++)
            mDataList.add(new Entry(i,i*i));
        LineDataSet mSet = new LineDataSet(mDataList,"Label");
        mSet.setColor(Color.RED);
        mSet.setValueTextColor(Color.BLUE);
        mChart.setData(new LineData(mSet));
        mChart.invalidate();
        Spinner mDaySpinner = mView.findViewById(R.id.chart_day);
        Spinner mClassSpinner = mView.findViewById(R.id.chart_class);
        String[] mDays = {"选择时间","近7天","近30天","近90天"};
        String[] mClass = {"肌肤问题","痘痘","黑眼圈","色斑"};
        mDaySpinner.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mDays));
        mClassSpinner.setAdapter(
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mClass));
        return mView;
    }
}
