package com.example.jungle.keepinmind1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jungle.keepinmind1.Activity.AddRecordActivity;
import com.example.jungle.keepinmind1.Activity.PassageActivity;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DataBaseUtils;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DateExchangeUtil;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BudgetDialog;
import com.example.jungle.keepinmind1.Utils.PublicUtil.ProgressBottle;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by jungle on 2017/12/13.
 */

public class PassageAdapter extends RecyclerView.Adapter<PassageAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 1;  //说明是不带有header
    private Context mContext;
    private ArrayList<ManageMoneyPassage> passagesList;
    private Button Accounting;
    private TextView year;
    private TextView month;
    private TextView monthIn;
    private TextView monthOut;
    private TextView monthUse;
    private TextView todayAdd;
    private TextView todayDecrease;
    private TextView weekAdd;
    private TextView weekDecrease;
    private TextView monthAdd;
    private TextView monthDecrease;
    private TextView todayDateLine;
    private TextView weekDateLine;
    private TextView monthDateLine;
    private TextView yearDateLine;
    private TextView yearAdd;
    private TextView yearDecrease;
    private ProgressBottle pb;
    private LinearLayout linearLayout2;

    public PassageAdapter(Context context, ArrayList<ManageMoneyPassage> list) {
        mContext = context;
        passagesList = list;

    }

    @Override
    public PassageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailbill_header, parent, false);
            Accounting = (Button) view.findViewById(R.id.Accounting);
            Accounting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddRecordActivity.class);
                    mContext.startActivity(intent);
                }
            });
            year = (TextView) view.findViewById(R.id.year);
            todayAdd = (TextView) view.findViewById(R.id.today_add);
            todayDecrease = (TextView) view.findViewById(R.id.today_decrease);
            month = (TextView) view.findViewById(R.id.month);
            weekAdd = (TextView) view.findViewById(R.id.week_add);
            monthIn = (TextView) view.findViewById(R.id.monthIn);
            weekDecrease = (TextView) view.findViewById(R.id.week_decrease);
            monthAdd = (TextView) view.findViewById(R.id.month_add);
            monthOut = (TextView) view.findViewById(R.id.monthOut);
            monthDecrease = (TextView) view.findViewById(R.id.month_decrease);
            todayDateLine = (TextView) view.findViewById(R.id.today_dateLine);
            monthUse = (TextView) view.findViewById(R.id.monthUse);
            weekDateLine = (TextView) view.findViewById(R.id.week_dateLine);
            monthDateLine = (TextView) view.findViewById(R.id.month_dateLine);
            yearDateLine = (TextView) view.findViewById(R.id.year_dateLine);
            yearAdd = (TextView) view.findViewById(R.id.year_add);
            yearDecrease = (TextView) view.findViewById(R.id.year_decrease);
            pb= (ProgressBottle) view.findViewById(R.id.pb);
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int width1 = wm.getDefaultDisplay().getWidth();
            ViewGroup.LayoutParams lp;
            lp=pb.getLayoutParams();
            lp.width=width1/5;
            lp.height=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 130,mContext.getResources().getDisplayMetrics());;
            pb.setLayoutParams(lp);
            linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);


        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passage_item, parent, false);
        }
        PassageAdapter.ViewHolder holder = new PassageAdapter.ViewHolder(view, viewType);

        return holder;
    }

    @Override
    public void onBindViewHolder(final PassageAdapter.ViewHolder holder, int position) {
        if (position == 0) {
            year.setText("/" + DateExchangeUtil.stampToYear(System.currentTimeMillis() + ""));
            month.setText(DateExchangeUtil.stampToMonth(System.currentTimeMillis() + ""));
            try {
                monthUse.setText("￥"+MathUtils.budget);
                monthIn.setText("￥" + MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "in"))) + "元");
                monthOut.setText("￥" + MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "out"))) + "元");
                todayAdd.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getTodayDate(System.currentTimeMillis())), System.currentTimeMillis() + "", "in"))) + "");
                todayDecrease.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getTodayDate(System.currentTimeMillis())), System.currentTimeMillis() + "", "out"))) + "");
                weekAdd.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getWeekStartDate()), System.currentTimeMillis() + "", "in"))) + "");
                weekDecrease.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getWeekStartDate()), System.currentTimeMillis() + "", "out"))) + "");
                monthAdd.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "in"))) + "");
                monthDecrease.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "out"))) + "");
                yearAdd.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getYearFirstDate(Integer.parseInt(DateExchangeUtil.stampToYear(System.currentTimeMillis() + "")))), System.currentTimeMillis() + "", "in"))) + "");
                yearDecrease.setText(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getYearFirstDate(Integer.parseInt(DateExchangeUtil.stampToYear(System.currentTimeMillis() + "")))), System.currentTimeMillis() + "", "out"))) + "");
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new BudgetDialog((Activity) mContext).show();
                    }
                });
                pb.set(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "out"))),MathUtils.budget);
                pb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            pb.set(MathUtils.format(DataBaseUtils.sumMoney(DataBaseUtils.queryUseType(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()), System.currentTimeMillis() + "", "out"))),MathUtils.budget);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
            todayDateLine.setText(DateExchangeUtil.getTodayTime(System.currentTimeMillis() + ""));
            weekDateLine.setText(DateExchangeUtil.getWeekStartTime() + " — " + DateExchangeUtil.getTodayTime(System.currentTimeMillis() + ""));
            monthDateLine.setText(DateExchangeUtil.getMonthStartTime() + " — " + DateExchangeUtil.getTodayTime(System.currentTimeMillis() + ""));
            yearDateLine.setText(DateExchangeUtil.getCurrYearFirst() + " — " + DateExchangeUtil.getTodayTime(System.currentTimeMillis() + ""));
        } else {
            final ManageMoneyPassage passage = passagesList.get(position);
            holder.passage_content.setText(passage.getPassageTitle());
            Glide.with(mContext).load(passage.getPassageImg()).into(holder.passage_img);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PassageActivity.class);
                    intent.putExtra("Title",passage.getPassageTitle());
                    intent.putExtra("Content",passage.getPassageContent());
                    intent.putExtra("Img",passage.getPassageImg());
                    mContext.startActivity(intent);
//                    mContext.startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.passage_img, "share").toBundle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return passagesList.size();
    }

    public int getItemViewType(int position) {

        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView passage_img;
        private TextView passage_content;
        private View view;


//header


        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_HEADER) {
                view = itemView;
            } else {
                view = itemView;
                passage_img = (ImageView) itemView.findViewById(R.id.passage_img);
                passage_content = (TextView) itemView.findViewById(R.id.passage_content);
            }

        }
    }
}
