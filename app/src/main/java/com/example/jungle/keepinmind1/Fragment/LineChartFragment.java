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

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LineChartFragment extends Fragment {

    private ArrayList<String> date;//X轴的标注
    int[] score ;//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<PointValue> mPointValues1 = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private LineChartView lineChart;
    private int max;
    public LineChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_line_chart, container, false);
        lineChart = (LineChartView) view.findViewById(R.id.chart);

        try {
            getAxisXLables();//获取x轴的标注
            getAxisPoints();//获取坐标点
            initLineChart();//初始化
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }
    private void getAxisXLables() {
        date = DateExchangeUtil.allWeekdays();
        for (int i = 0; i < date.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date.get(i)));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() throws ParseException {
        float max1 = 0;
        for (int i = 0; i < date.size(); i++) {
            float tmp = (float) DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(date.get(i) + " 00:00:00"), DateExchangeUtil.dateToStamp(date.get(i) + " 23:59:59"), "in"));
            if (tmp > max1) {
                max1 = tmp;
            }
            mPointValues.add(new PointValue(i, tmp));
        }
        for (int i = 0; i < date.size(); i++) {
            float tmp = (float) DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(date.get(i) + " 00:00:00"), DateExchangeUtil.dateToStamp(date.get(i) + " 23:59:59"), "out"));
            if (tmp > max1) {
                max1 = tmp;
            }
            mPointValues1.add(new PointValue(i, tmp));
        }
        max = (int) max1 + 10;
    }

    /*
    *
    * */
    private void initLineChart() throws ParseException {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.DIAMOND);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        Line line1 = new Line(mPointValues1).setColor(Color.rgb(106,215,158));
        line1.setShape(ValueShape.CIRCLE);
        line1.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line1.setFilled(false);//是否填充曲线的面积
//        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line1.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line1.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line1.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line1);
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
//        axisX.setName("收入折线图");  //表格名称
        axisX.setTextSize(10);//设置字体大小
//        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("收入支出");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        axisY.setHasLines(true);
        axisY.setTextColor(Color.BLACK);
//        axisY.setMaxLabelChars(6);//max label length, for example 60
        List<AxisValue> values = new ArrayList<>();
        while (max % 10 != 0) {
            max += 1;
        }
        System.out.println("max最大值："+max);
        int j = max / 10;
        for (int i = 0; i < max; i += j) {
            AxisValue value = new AxisValue(i);
            String label = "" + i;
            value.setLabel(label);
            values.add(value);
        }
        axisY.setValues(values);


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);


    }

}
