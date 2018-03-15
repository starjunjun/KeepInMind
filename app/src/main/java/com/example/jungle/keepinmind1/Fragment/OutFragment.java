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
public class OutFragment extends Fragment {
    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options4Items = new ArrayList<>();
    private LinearLayout Linear2;
    private LinearLayout Linear3;
    private TextView Account;
    private Button ok_button;
    private TextView type1;
    private Activity mActivity;
    private ArrayList<String> data;
    private TextView type2;
    private TextView outMoney;
    private com.rengwuxian.materialedittext.MaterialEditText remarks;

    public OutFragment() {
        // Required empty public constructor
    }

    public OutFragment(Activity activity) {
        mActivity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_out, container, false);
        type1 = (TextView) view.findViewById(R.id.textView6);
        type2 = (TextView) view.findViewById(R.id.textView5);
        Account = (TextView) view.findViewById(R.id.account);
        ok_button = (Button) view.findViewById(R.id.ok_button);
        Linear2 = (LinearLayout) view.findViewById(R.id.Linear2);
        Linear3 = (LinearLayout) view.findViewById(R.id.Linear3);
        outMoney = (TextView) view.findViewById(R.id.out_money);
        remarks = (MaterialEditText) view.findViewById(R.id.edittext);
//        outMoney.setInputType(EditorInfo.TYPE_CLASS_PHONE);
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
//        outMoney.setKeyListener(listener);
        outMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new keyboard(mActivity).show();
            }
        });

        initData();
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money = Double.valueOf(outMoney.getText().toString()).doubleValue();
                if(remarks.getText()!=null){
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money,Account.getText().toString(),"out",remarks.getText().toString(),System.currentTimeMillis(),type1.getText().toString()+type2.getText().toString());
                    DataBaseUtils.add(dbBean);
                }else{
                    ManageMoneyDBBean dbBean = new ManageMoneyDBBean(money,Account.getText().toString(),"out",System.currentTimeMillis(),type1.getText().toString()+type2.getText().toString());
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
        options1Items.add("衣服饰品");
        options1Items.add("食品酒水");
        options1Items.add("居家物业");
        options1Items.add("行车交通");
        options1Items.add("交流通讯");
        options1Items.add("休闲娱乐");
        options1Items.add("学习进修");
        options1Items.add("人情往来");
        options1Items.add("医疗保健");
        options1Items.add("金融保险");
        options1Items.add("其他杂项");
        data = new ArrayList<>();
        data.add("衣服裤子");
        data.add("鞋帽包包");
        data.add("化妆饰品");
        options2Items.add(data);
        ArrayList<String> data1 = new ArrayList<>();
        data1.add("早午晚餐");
        data1.add("烟酒茶");
        data1.add("水果零食");
        options2Items.add(data1);
        ArrayList<String> data2 = new ArrayList<>();
        data2.add("日常用品");
        data2.add("水电煤气");
        data2.add("房租");
        data2.add("物业管理");
        data2.add("维修保养");
        options2Items.add(data2);
        ArrayList data3 = new ArrayList<>();
        data3.add("公共交通");
        data3.add("打车租车");
        data3.add("私家车费用");
        options2Items.add(data3);
        ArrayList data4 = new ArrayList<>();
        data4.add("座机费");
        data4.add("手机费");
        data4.add("上网费");
        data4.add("邮寄费");
        options2Items.add(data4);
        ArrayList data5 = new ArrayList<>();
        data5.add("运动健身");
        data5.add("腐败聚会");
        data5.add("休闲玩乐");
        data5.add("宠物玩具");
        data5.add("旅游度假");
        options2Items.add(data5);
        ArrayList data6 = new ArrayList<>();
        data6.add("书报杂志");
        data6.add("培训进修");
        data6.add("数码装备");
        options2Items.add(data6);
        ArrayList data7 = new ArrayList<>();
        data7.add("送礼请客");
        data7.add("孝敬家长");
        data7.add("还人钱物");
        data7.add("慈善捐助");
        options2Items.add(data7);
        ArrayList data8 = new ArrayList<>();
        data8.add("药品费");
        data8.add("保健费");
        data8.add("美容费");
        data8.add("治疗费");
        options2Items.add(data8);
        ArrayList data9 = new ArrayList<>();
        data9.add("银行手续");
        data9.add("投资亏损");
        data9.add("按揭还款");
        data9.add("消费税收");
        data9.add("利息支出");
        data9.add("赔偿罚款");
        options2Items.add(data9);
        ArrayList data10 = new ArrayList<>();
        data10.add("其他支出");
        data10.add("意外丢失");
        data10.add("烂账损失");
        options2Items.add(data10);

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
            outMoney.setText(eventPostBean.getNum()+"");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
