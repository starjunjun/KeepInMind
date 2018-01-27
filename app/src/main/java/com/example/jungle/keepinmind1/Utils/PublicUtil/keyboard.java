package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jungle.keepinmind1.Bean.EventPostBean;
import com.example.jungle.keepinmind1.R;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

/**
 * Created by jungle on 2018/1/20.
 */

public class keyboard extends Dialog implements View.OnClickListener {
    private TextView k0;
    private TextView k1;
    private TextView k2;
    private TextView k3;
    private TextView k4;
    private TextView k5;
    private TextView k6;
    private TextView k7;
    private TextView k8;
    private TextView k9;
    private TextView kd;
    private TextView ok;
    private TextView kc;
    private TextView show;

    private Activity mActivity;

    public keyboard(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard);
        init();
        setViewLocation();
        setCanceledOnTouchOutside(true);
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (height * 0.45);
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    private void init() {
        k0 = (TextView) findViewById(R.id.k0);
        k1 = (TextView) findViewById(R.id.k1);
        k2 = (TextView) findViewById(R.id.k2);
        k3 = (TextView) findViewById(R.id.k3);
        k4 = (TextView) findViewById(R.id.k4);
        k5 = (TextView) findViewById(R.id.k5);
        k6 = (TextView) findViewById(R.id.k6);
        k7 = (TextView) findViewById(R.id.k7);
        k8 = (TextView) findViewById(R.id.k8);
        k9 = (TextView) findViewById(R.id.k9);
        kd = (TextView) findViewById(R.id.kd);
        kc = (TextView) findViewById(R.id.kc);
        show = (TextView) findViewById(R.id.show);
        ok = (TextView) findViewById(R.id.ok);
        k0.setOnClickListener(this);
        k1.setOnClickListener(this);
        k2.setOnClickListener(this);
        k3.setOnClickListener(this);
        k4.setOnClickListener(this);
        k5.setOnClickListener(this);
        k6.setOnClickListener(this);
        k7.setOnClickListener(this);
        k8.setOnClickListener(this);
        k9.setOnClickListener(this);
        kc.setOnClickListener(this);
        kd.setOnClickListener(this);
        ok.setOnClickListener(this);
        show.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.k0:
                if (!show.getText().equals("")) show.setText(show.getText() + "0");
                break;
            case R.id.k1:
                show.setText(show.getText() + "1");
                break;
            case R.id.k2:
                show.setText(show.getText() + "2");
                break;
            case R.id.k3:
                show.setText(show.getText() + "3");
                break;
            case R.id.k4:
                show.setText(show.getText() + "4");
                break;
            case R.id.k5:
                show.setText(show.getText() + "5");
                break;
            case R.id.k6:
                show.setText(show.getText() + "6");
                break;
            case R.id.k7:
                show.setText(show.getText() + "7");
                break;
            case R.id.k8:
                show.setText(show.getText() + "8");
                break;
            case R.id.k9:
                show.setText(show.getText() + "9");
                break;
            case R.id.kd:
                show.setText(show.getText() + ".");
                break;
            case R.id.kc:
                show.setText(show.getText().subSequence(0, show.getText().length() - 1));
                break;
            case R.id.ok:
                System.out.println(show.getText());
                EventBus.getDefault().post(new EventPostBean(Double.parseDouble(show.getText().toString()),mActivity));
                cancel();
                break;
        }
    }
}
