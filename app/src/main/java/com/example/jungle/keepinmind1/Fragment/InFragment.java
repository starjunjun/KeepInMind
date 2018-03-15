package com.example.jungle.keepinmind1.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.jungle.keepinmind1.Bean.EventPostBean;
import com.example.jungle.keepinmind1.Bean.ManageMoneyDBBean;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DataBaseUtils;
import com.example.jungle.keepinmind1.Utils.PublicUtil.keyboard;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InFragment extends Fragment {
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options4Items = new ArrayList<>();
    private TextView in_money;
    private LinearLayout Linear2;
    private LinearLayout Linear3;
    private Button ok_button;
    private TextView type1;
    private TextView type2;
    private TextView Account;
    private com.rengwuxian.materialedittext.MaterialEditText remarks;
    private Activity mActivity;


    private ArrayList<String> data;

    public InFragment(Activity activity) {
        mActivity = activity;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_in, container, false);
        Linear2 = (LinearLayout) view.findViewById(R.id.Linear2);
        Linear3 = (LinearLayout) view.findViewById(R.id.Linear3);
        ok_button = (Button) view.findViewById(R.id.ok_button);
        type1 = (TextView) view.findViewById(R.id.type1);
        type2 = (TextView) view.findViewById(R.id.textView5);
        Account = (TextView) view.findViewById(R.id.account);
        in_money = (TextView) view.findViewById(R.id.moneyTv);
        remarks = (MaterialEditText) view.findViewById(R.id.edittext);
//        in_money.setInputType(EditorInfo.TYPE_CLASS_PHONE);
//
//        KeyListener listener = new NumberKeyListener() {
//
//            /**
//             * @return ：返回哪些希望可以被输入的字符,默认不允许输入
//             */
//            @Override
//            protected char[] getAcceptedChars() {
//                char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
//                return chars;
////            return new char[0];
//            }
//
//            /**
//             * 0：无键盘,键盘弹不出来
//             * 1：英文键盘
//             * 2：模拟键盘
//             * 3：数字键盘
//             *
//             * @return
//             */
//            @Override
//            public int getInputType() {
//                return 3;
//            }
//        };
//        in_money.setKeyListener(listener);
        Linear2.requestFocus();
        in_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new keyboard(mActivity).show();
            }
        });
        initData();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money = Double.valueOf(in_money.getText().toString()).doubleValue();
                if (remarks.getText() != null) {
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money, Account.getText().toString(), "in", remarks.getText().toString(), System.currentTimeMillis(), type1.getText().toString() + type2.getText().toString());
                    DataBaseUtils.add(dbBean);
                } else {
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money, Account.getText().toString(), "in", System.currentTimeMillis(), type1.getText().toString() + type2.getText().toString());
                    DataBaseUtils.add(dbBean);
                }

                EventBus.getDefault().post("lolo");
                mActivity.finish();


            }
        });
        Linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1) + " > ";
                        type1.setText(tx);
                        type2.setText(options2Items.get(options1).get(option2));
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items);
                pvOptions.show();
            }
        });

        Linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options4Items.get(options1).get(option2);
                        Account.setText(tx);
                    }
                }).build();
                pvOptions.setPicker(options3Items, options4Items);
                pvOptions.show();
            }
        });

        return view;
    }

    private void initData() {
        options1Items.add("职业收入");
        options1Items.add("其他收入");
        data = new ArrayList<>();
        data.add("工资收入");
        data.add("利息收入");
        data.add("加班收入");
        data.add("奖金收入");
        data.add("投资收入");
        data.add("兼职收入");
        options2Items.add(data);
        ArrayList<String> data1 = new ArrayList<>();
        data1.add("礼金收入");
        data1.add("中奖收入");
        data1.add("意外来钱");
        data1.add("经营所得");
        data1.add("信用卡还款");
        options2Items.add(data1);

        options3Items.add("现金");
        options3Items.add("信用卡");
        options3Items.add("金融账户");
        options3Items.add("虚拟账户");
        options3Items.add("负债");
        options3Items.add("债权");
        ArrayList<String> al1 = new ArrayList<>();
        al1.add("现金(CNY)");
        options4Items.add(al1);
        ArrayList<String> al2 = new ArrayList<>();
        al2.add("信用卡(CNY)");
        options4Items.add(al2);
        ArrayList<String> al3 = new ArrayList<>();
        al3.add("银行卡(CNY)");
        options4Items.add(al3);
        ArrayList<String> al4 = new ArrayList<>();
        al4.add("公交卡(CNY)");
        al4.add("饭卡(CNY)");
        al4.add("支付宝(CNY)");
        options4Items.add(al4);
        ArrayList<String> al5 = new ArrayList<>();
        al5.add("应付款项(CNY)");
        options4Items.add(al5);
        ArrayList<String> al6 = new ArrayList<>();
        al6.add("应收款项(CNY)");
        al6.add("公司报销(CNY)");
        options4Items.add(al6);
        ArrayList<String> al7 = new ArrayList<>();
        al7.add("基金账户(CNY)");
        al7.add("余额宝(CNY)");
        al7.add("股票账户(CNY)");
        options4Items.add(al7);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(EventPostBean eventPostBean) {
        if(eventPostBean.getActivity()==mActivity){
            in_money.setText(eventPostBean.getNum()+"");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
