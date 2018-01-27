package com.example.jungle.keepinmind1.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jungle.keepinmind1.R;

/**
 * Created by jungle on 2017/12/29.
 */

public class MoneyTextView extends android.support.v7.widget.AppCompatEditText {
    private TextView moneyTv;

    public MoneyTextView(Context context) {
        super(context);
    }

    public MoneyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        View view = View.inflate(context, R.layout.moneytextview, this);
//        moneyTv = (TextView) view.findViewById(R.id.moneyTv);
//        moneyTv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        });
    }

    public MoneyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
