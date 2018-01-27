package com.example.jungle.keepinmind1.Fragment;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DataBaseUtils;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DateExchangeUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PieChartFragment extends Fragment {


    private PieChartView inPieChart;
    private PieChartView outPieChart;
    private int[] colors = {Color.rgb(166, 202, 214), Color.rgb(186, 227, 223), Color.rgb(166, 202, 214), Color.rgb(199, 224, 120), Color.rgb(226, 224, 120), Color.rgb(176, 222, 212), Color.rgb(238, 215, 125), Color.rgb(236, 193, 157), Color.rgb(245, 194, 194)};

    public PieChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_pie_chart, container, false);
        inPieChart = (PieChartView) view.findViewById(R.id.piechartIn);
        outPieChart = (PieChartView) view.findViewById(R.id.piechartOut);
        setPieIn();
        setPieOut();
        return view;
    }

    private void setPieOut() {
        outPieChart.setViewportCalculationEnabled(true);//设置饼图自动适应大小
        outPieChart.setChartRotation(270, true);//设置饼图旋转角度，且是否为动画
        outPieChart.setChartRotationEnabled(true);//设置饼图是否可以手动旋转
        PieChartData pd = new PieChartData();//实例化PieChartData对象
        pd.setHasLabels(true);
        pd.setHasLabelsOutside(false);//设置饼图外面是否显示值
        pd.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
        pd.setCenterCircleColor(Color.WHITE);//设置饼图中间圈的颜色
        pd.setCenterCircleScale((float) 0.4);////设置第二个圈的大小比例
        pd.setCenterText1("账户支出");//设置文本
        pd.setCenterText1Color(Color.BLACK);//设置文本颜色
        pd.setCenterText1FontSize(18);//设置文本大小
        pd.setValueLabelsTextColor(Color.WHITE);//设置显示值的字体颜色
        pd.setSlicesSpacing(2);//设置数据间的间隙
        pd.setHasLabelsOnlyForSelected(false);//设置当值被选中才显示
        List<SliceValue> sliceList1 = new ArrayList<SliceValue>();
        Cursor c = null;
        try {
            int i = 0;
            c = DataBaseUtils.queryGroupByAccount(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "out");
            while (c.moveToNext()) {
                sliceList1.add(new SliceValue((float) c.getDouble(0), colors[i], 1).setLabel(c.getString(1)));
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();

        } finally {
            if (c != null) c.close();
        }
        pd.setValues(sliceList1);//为饼图添加数据
        outPieChart.setPieChartData(pd);//将数据设置给饼图
    }

    private void setPieIn() {
        //        pieChart.setCircleFillRatio( float fillRatio);//设置饼图其中的比例
//        pieChart.setCircleOval(RectF orginCircleOval);//设置饼图成椭圆形
//        pieChart.setPieChartData(PieChartData data);//为饼图设置数据
//        pd.setCenterText1Typeface(Typeface text1Typeface);//设置文本字体
//        pd.setCenterText2("456");//设置第二个圈文本
//        pd.setCenterText2Color(Color.);//设置第二个圈文本颜色
//        pd.setCenterText2Typeface(Typeface text2Typeface);//设置第二个圈文本字体
        inPieChart.setViewportCalculationEnabled(true);//设置饼图自动适应大小
        inPieChart.setChartRotation(270, true);//设置饼图旋转角度，且是否为动画
        inPieChart.setChartRotationEnabled(true);//设置饼图是否可以手动旋转
        PieChartData pd = new PieChartData();//实例化PieChartData对象
        pd.setHasLabels(true);
        pd.setHasLabelsOutside(false);//设置饼图外面是否显示值
        pd.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
        pd.setCenterCircleColor(Color.WHITE);//设置饼图中间圈的颜色
        pd.setCenterCircleScale((float) 0.4);////设置第二个圈的大小比例
        pd.setCenterText1("账户收入");//设置文本
        pd.setCenterText1Color(Color.BLACK);//设置文本颜色
        pd.setCenterText1FontSize(18);//设置文本大小
        pd.setValueLabelsTextColor(Color.WHITE);//设置显示值的字体颜色
        pd.setSlicesSpacing(2);//设置数据间的间隙
        pd.setHasLabelsOnlyForSelected(false);//设置当值被选中才显示
        List<SliceValue> sliceList = new ArrayList<SliceValue>();
        Cursor c = null;
        try {
            int i = 0;
            c = DataBaseUtils.queryGroupByAccount(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "in");
            while (c.moveToNext()) {
                sliceList.add(new SliceValue((float) c.getDouble(0), colors[i], 1).setLabel(c.getString(1)));
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();

        } finally {
            if (c != null) c.close();
        }
        pd.setValues(sliceList);//为饼图添加数据
        inPieChart.setPieChartData(pd);//将数据设置给饼图


    }

}
