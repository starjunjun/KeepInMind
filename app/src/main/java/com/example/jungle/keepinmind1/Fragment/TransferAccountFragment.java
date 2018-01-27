package com.example.jungle.keepinmind1.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class TransferAccountFragment extends Fragment {
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private Button ok_button;
    private LinearLayout in;
    private LinearLayout out;
    private TextView inMoney;
    private TextView outMoney;
    private TextView type1;
    private Activity mActivity;
    private LinearLayout Linear2;
    private EditText moneyTv;
    private TextView Account;
    private com.rengwuxian.materialedittext.MaterialEditText remarks;


    public TransferAccountFragment() {
        // Required empty public constructor
    }
    public TransferAccountFragment(Activity activity) {
        // Required empty public constructor
        mActivity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_transfer_account, container, false);
        Linear2 = (LinearLayout) view.findViewById(R.id.Linear2);
        inMoney = (TextView) view.findViewById(R.id.in_money);
        outMoney = (TextView) view.findViewById(R.id.out_money);
        in = (LinearLayout) view.findViewById(R.id.in);
        out = (LinearLayout) view.findViewById(R.id.out);
        ok_button = (Button) view.findViewById(R.id.ok_button);
        type1 = (TextView) view.findViewById(R.id.type1);
        moneyTv = (EditText) view.findViewById(R.id.moneyTv);
        remarks = (MaterialEditText) view.findViewById(R.id.edittext);
        Account = (TextView) view.findViewById(R.id.account);
//        moneyTv.setInputType(EditorInfo.TYPE_CLASS_PHONE);
//
//        KeyListener listener = new NumberKeyListener() {
//
//            /**
//             * @return ：返回哪些希望可以被输入的字符,默认不允许输入
//             */
//            @Override
//            protected char[] getAcceptedChars() {
//                char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'};
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
//        moneyTv.setKeyListener(listener);
        moneyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new keyboard(mActivity).show();
            }
        });
        initData();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money = Double.valueOf(moneyTv.getText().toString()).doubleValue();
                if(remarks.getText()!=null){
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money,Account.getText().toString(),"transfer",remarks.getText().toString(),System.currentTimeMillis(),outMoney.getText().toString()+inMoney.getText().toString());
                    DataBaseUtils.add(dbBean);
                }else{
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money,Account.getText().toString(),"transfer",System.currentTimeMillis(),outMoney.getText().toString()+inMoney.getText().toString());
                    DataBaseUtils.add(dbBean);
                }

                EventBus.getDefault().post("lolo");
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
                        inMoney.setText(options2Items.get(0).get(option2));
                        String tx =options1Items.get(options1);
                        outMoney.setText(tx);
                    }
                }).setLinkage(false).build();
                pvOptions.setPicker(options1Items, options2Items);
                pvOptions.show();
            }
        });
        return view;
    }

    private void initData() {
        options1Items.add("现金(CNY)");
        options1Items.add("银行(CNY)");
        options1Items.add("公交卡(CNY)");
        options1Items.add("饭卡(CNY)");
        options1Items.add("支付宝(CNY)");
        options1Items.add("信用卡(CNY)");
        options1Items.add("应付款项(CNY)");
        options1Items.add("应收款项(CNY)");
        options1Items.add("公司报销(CNY)");
        options1Items.add("基金账户(CNY)");
        options1Items.add("余额宝(CNY)");
        options1Items.add("股票账户(CNY)");
        ArrayList<String> data1 = new ArrayList<>();
        data1.add("现金(CNY)");
        data1.add("银行(CNY)");
        data1.add("公交卡(CNY)");
        data1.add("饭卡(CNY)");
        data1.add("支付宝(CNY)");
        data1.add("信用卡(CNY)");
        data1.add("应付款项(CNY)");
        data1.add("应收款项(CNY)");
        data1.add("公司报销(CNY)");
        data1.add("基金账户(CNY)");
        data1.add("余额宝(CNY)");
        data1.add("股票账户(CNY)");
        options2Items.add(data1);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(EventPostBean eventPostBean) {
        if(eventPostBean.getActivity()==mActivity){
            moneyTv.setText(eventPostBean.getNum()+"");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
