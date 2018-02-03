package com.example.jungle.keepinmind1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jungle.keepinmind1.Activity.PassageActivity;
import com.example.jungle.keepinmind1.Bean.ManageMoneyPassage;
import com.example.jungle.keepinmind1.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by jungle on 2017/12/15.
 */

public class RollViewAdapter extends StaticPagerAdapter {
    private List<ManageMoneyPassage> list;
    private Context mContext;

    public RollViewAdapter(List<ManageMoneyPassage> list, Context context) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(mContext).load(list.get(position).getPassageImg()).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PassageActivity.class);
                intent.putExtra("Title",list.get(position).getPassageTitle());
                intent.putExtra("Content",list.get(position).getPassageContent());
                intent.putExtra("Img",list.get(position).getPassageImg());
                mContext.startActivity(intent);
            }
        });
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }
}

