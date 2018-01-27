package com.example.jungle.keepinmind1.Fragment;


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

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColumnChartFragment extends Fragment {

    private int max;
    private ColumnChartView columnChartView;
    private String[] xDate={"现金(CNY)","信用卡(CNY)","银行卡(CNY)","公交卡(CNY)","饭卡(CNY)","支付宝(CNY)","应付款项(CNY)","应收款项(CNY)","公司报销(CNY)","基金账户(CNY)","余额宝(CNY)","股票账户(CNY)"};

    public ColumnChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_column_chart, container, false);
        columnChartView = (ColumnChartView) view.findViewById(R.id.ColumnChart);
        columnChartView.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, int i1, SubcolumnValue subcolumnValue) {

            }

            @Override
            public void onValueDeselected() {

            }
        });
        try {
            generateData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void generateData() throws ParseException {
        //一个柱状图需要一个柱子集合
        List<Column> columnList = new ArrayList<>();
        //每根柱子又可以分为多根柱子
        List<SubcolumnValue> subcolumnValueList;
        int columns = 12;
        float max1=0;
        for (int i = 0; i < columns; i++) {

            subcolumnValueList = new ArrayList<>();

            float in = (float) DataBaseUtils.sumMoney(DataBaseUtils.queryUseAccountType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "",xDate[i], "in"));
            if(in>max1)max1 = in;
            float out = (float) DataBaseUtils.sumMoney(DataBaseUtils.queryUseAccountType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "",xDate[i], "out"));
            if(out >max1)max1=out;
            subcolumnValueList.add(new SubcolumnValue(in, ChartUtils.COLOR_GREEN));
            subcolumnValueList.add(new SubcolumnValue(out, ChartUtils.COLOR_RED));
            //每根柱子需要一个子柱子集合
            Column column = new Column(subcolumnValueList);
            //这一步是能让圆柱标注数据显示带小数的重要一步
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
            column.setFormatter(chartValueFormatter);
            column.setHasLabels(true);//是否直接显示标注（其它的一些设置类似折线图）
            columnList.add(column);
        }
        List<AxisValue> values = new ArrayList<>();
        max = (int) (max1+10);
        while(max%10!=0)max+=1;
        int j = max / 10;
        for (int i = 0; i < max; i += j) {
            AxisValue value = new AxisValue(i);
            String label = "" + i;
            value.setLabel(label);
            values.add(value);
        }
        ColumnChartData data = new ColumnChartData(columnList);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setValues(values);
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(5);
        axisY.setName("收支");
        axisY.setTextSize(10);
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(Color.BLACK);//颜色
        //给轴设置值
        List<AxisValue> list = new ArrayList<>();
        for (int i = 0; i < xDate.length; i++) {
            list.add(new AxisValue(i).setLabel(xDate[i]));//i代表位置，label则是在轴上该位置的标注
        }
        //给x轴设置值
        axisX.setValues(list);
        axisX.setTextColor(Color.BLACK);
        axisX.setHasTiltedLabels(true);
        data.setAxisXBottom(axisX);
        //设置是否让多根子柱子在同一根柱子上显示（会以断层的形式分开)
        data.setStacked(false);
        data.setAxisYLeft(axisY);
        columnChartView.setMaxZoom((float) 2);
        columnChartView.setViewportCalculationEnabled(true);
        columnChartView.setZoomType(ZoomType.HORIZONTAL);
        columnChartView.setZoomEnabled(true);
        columnChartView.setColumnChartData(data);
        Viewport v = new Viewport(columnChartView.getMaximumViewport());
        v.left = 0;
        v.right = 4;
        columnChartView.setCurrentViewport(v);

    }

}
