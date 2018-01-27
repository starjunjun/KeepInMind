package com.example.jungle.keepinmind1.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jungle.keepinmind1.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by jungle on 2017/12/15.
 */

public class RollViewAdapter extends StaticPagerAdapter {
    private int[] imgs = {
            R.drawable.edit_new,
            R.drawable.user_info_bg,
            R.drawable.edit_new,
            R.drawable.user_info_bg,
    };


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getCount() {
        return imgs.length;
    }
}

