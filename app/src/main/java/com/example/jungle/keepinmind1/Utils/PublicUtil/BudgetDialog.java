package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

/**
 * Created by jungle on 2018/1/31.
 */

public class BudgetDialog extends Dialog {
    private TextView tv;
    private EditText ed;
    private TextView bt;


    private Activity mActivity;

    public BudgetDialog(Activity activity) {
        super(activity, R.style.MyDialog);
        this.mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_dialog);
        init();
        setViewLocation();
        setCanceledOnTouchOutside(true);
    }

    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels * 4 / 5;
        lp.height = dm.heightPixels * 1 / 4;
        // 设置dialog宽度为屏幕的4/5
        onWindowAttributesChanged(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
    }

    private void init() {
        tv = (TextView) findViewById(R.id.tv);
        ed = (EditText) findViewById(R.id.ed);
        bt = (TextView) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ed.getText().toString().trim().equals("")) {
                    Log.i(TAG, "onClick: " + ed.getText().toString().trim());
                    MathUtils.budget=Double.parseDouble(ed.getText().toString().trim());
                    SharedPreferences settings = mActivity.getSharedPreferences("data", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putFloat("budget", (float) MathUtils.budget);
                    editor.commit();
                    EventBus.getDefault().post("budget");
                    dismiss();
                }
            }
        });
    }


}
