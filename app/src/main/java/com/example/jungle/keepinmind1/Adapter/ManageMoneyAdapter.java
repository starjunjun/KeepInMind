package com.example.jungle.keepinmind1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jungle.keepinmind1.Activity.GuPiaoContentActivity;
import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.JiJinBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.RecycleViewDivider;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.*;

/**
 * Created by jungle on 2017/12/15.
 */

public class ManageMoneyAdapter extends RecyclerView.Adapter<ManageMoneyAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMALJ = 1;  //说明是不带有header
    public static final int TYPE_NORMALG = 2;
    private List mList;
    private RollPagerView mRollViewPager;
    private Context mContext;
    private boolean isJiJin;  //true代表基金，false代表股票
    private TabLayout tabLayout;
    private List<ManageMoneyPassage> list;

    public ManageMoneyAdapter(List list, Context context, boolean isJiJin) {
        mList = list;
        mContext = context;
        this.isJiJin = isJiJin;
    }

    @Override
    public ManageMoneyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        isJiJin = JiJinBean.class.isInstance(mList.get(0));
        if (viewType == TYPE_HEADER) {
            list = new ArrayList<>();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.managemoney_header, parent, false);
            mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

            NetRequestFactory.getInstance().createService(MyService.class).getAd().compose(Transform.<RetrunJson<List<ManageMoneyPassage>>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<List<ManageMoneyPassage>>>() {
                @Override
                public void onSuccess(RetrunJson<List<ManageMoneyPassage>> rj) {
                    //设置播放时间间隔
                    mRollViewPager.setPlayDelay(3000);
                    //设置透明度
                    mRollViewPager.setAnimationDurtion(2000);
                    list.addAll((ArrayList<ManageMoneyPassage>) rj.getResult());
                    mRollViewPager.setAdapter(new RollViewAdapter(list,mContext));
                    mRollViewPager.setHintView(new ColorPointHintView(mContext, Color.YELLOW, Color.WHITE));
                }

                @Override
                public void _onError(RetrunJson<List<ManageMoneyPassage>> rj) {

                }

            });



            //设置指示器（顺序依次）
            //自定义指示器图片
            //设置圆点指示器颜色
            //设置文字指示器
            //隐藏指示器
            //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));

            //mRollViewPager.setHintView(new TextHintView(this));
            //mRollViewPager.setHintView(null);
            tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("基金"));
            tabLayout.addTab(tabLayout.newTab().setText("股票"));
            tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
                @Override
                public void onTabSelected(Tab tab) {
                    if (tab.getText().equals("基金")) {
                        EventBus.getDefault().post(true);
                    } else if (tab.getText().equals("股票")) {
                        EventBus.getDefault().post(false);
                    }
                }

                @Override
                public void onTabUnselected(Tab tab) {

                }

                @Override
                public void onTabReselected(Tab tab) {

                }
            });


        } else if (viewType ==TYPE_NORMALJ) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.managemoney_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gupiao_item, parent, false);
        }
        ManageMoneyAdapter.ViewHolder holder = new ManageMoneyAdapter.ViewHolder(view, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ManageMoneyAdapter.ViewHolder holder, int position) {
        if (position == 0) {

        } else if (holder.type ==TYPE_NORMALJ) {
            JiJinBean jb = (JiJinBean) mList.get(position);
            holder.name.setText(jb.getName());
            holder.code.setText(jb.getCode());
            holder.netgrowrate.setText(jb.getNetgrowrate() + "%");
        } else if(holder.type == TYPE_NORMALG) {
            GuPiaoBean gb = (GuPiaoBean) mList.get(position);
            holder.name.setText(gb.getName());
            holder.code.setText(gb.getCode());
            holder.trade.setText(gb.getTrade());
            if (Double.valueOf(gb.getPricechange()) >= 0) {
                holder.upOrDown.setImageResource(R.drawable.up);
            } else {
                holder.upOrDown.setImageResource(R.drawable.down);
            }
            holder.view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, GuPiaoContentActivity.class);
                    intent.putExtra("searchCode",holder.code.getText().toString().trim());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getItemViewType(int position) {

        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }else if(JiJinBean.class.isInstance(mList.get(0))){
            return TYPE_NORMALJ;
        }
        return TYPE_NORMALG;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView netgrowrate;
        private TextView code;
        private TextView name;
        private TextView trade;
        private ImageView upOrDown;
        private int type;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_HEADER) {
                view = itemView;
            } else if (viewType ==TYPE_NORMALJ) {
                view = itemView;
                netgrowrate = (TextView) view.findViewById(R.id.netgrowrate);
                code = (TextView) view.findViewById(R.id.code);
                name = (TextView) view.findViewById(R.id.name);
                type =TYPE_NORMALJ;
            } else {
                view = itemView;
                trade = (TextView) view.findViewById(R.id.trade);
                code = (TextView) view.findViewById(R.id.code);
                name = (TextView) view.findViewById(R.id.name);
                upOrDown = (ImageView) view.findViewById(R.id.upOrDown);
                type = TYPE_NORMALG;
            }
        }
    }
}
