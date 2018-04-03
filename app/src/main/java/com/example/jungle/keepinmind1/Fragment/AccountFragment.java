package com.example.jungle.keepinmind1.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DataBaseUtils;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DateExchangeUtil;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private String currentTime;
    private String startTime;

    private TextView net_assets;
    private TextView cash;
    private TextView cashTotal;
    private TextView financeCardMoney;
    private TextView financeTotal;
    private TextView fictitiousTotal;
    private TextView busCardMoney;
    private TextView rideCardMoney;
    private TextView alipayMoney;
    private TextView creditTotal;
    private TextView creditCardMoney;
    private TextView liabilitiesTotal;
    private TextView liabilitiesMoney;
    private TextView creditorrightsTotal;
    private TextView creditorrightsMoney;
    private TextView companyReimbursement;
    private TextView investmentTotal;
    private TextView investmentMoney;
    private TextView investmentMoney1;
    private TextView investmentMoney2;
    private TextView liabilities;
    private TextView assets;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        currentTime = System.currentTimeMillis() + "";
        try {
            startTime = DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assets = (TextView) view.findViewById(R.id.assets);
        net_assets = (TextView) view.findViewById(R.id.net_assets);
        liabilities = (TextView) view.findViewById(R.id.liabilities);
        cash = (TextView) view.findViewById(R.id.cash);
        cash.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "现金(CNY)"))) + "");
        cashTotal = (TextView) view.findViewById(R.id.cash_total);
        cashTotal.setText(cash.getText());
        financeCardMoney = (TextView) view.findViewById(R.id.finance_card_money);
        financeCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "银行卡(CNY)"))) + "");
        financeTotal = (TextView) view.findViewById(R.id.finance_total);
        financeTotal.setText(financeCardMoney.getText());
        busCardMoney = (TextView) view.findViewById(R.id.bus_card_money);
        rideCardMoney = (TextView) view.findViewById(R.id.ride_card_money);
        alipayMoney = (TextView) view.findViewById(R.id.alipay_money);
        busCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "公交卡(CNY)"))) + "");
        rideCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "饭卡(CNY)"))) + "");
        alipayMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "支付宝(CNY)"))) + "");
        fictitiousTotal = (TextView) view.findViewById(R.id.fictitious_total);
        fictitiousTotal.setText(Double.parseDouble(busCardMoney.getText().toString()) + Double.parseDouble(rideCardMoney.getText().toString()) + Double.parseDouble(alipayMoney.getText().toString()) + "");
        creditTotal = (TextView) view.findViewById(R.id.credit_total);
        creditCardMoney = (TextView) view.findViewById(R.id.credit_card_money);
        creditCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "信用卡(CNY)"))) + "");
        creditTotal.setText(creditCardMoney.getText());
        liabilitiesTotal = (TextView) view.findViewById(R.id.liabilities_total);
        liabilitiesMoney = (TextView) view.findViewById(R.id.liabilities_money);
        liabilitiesMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "应付款项(CNY)"))) + "");
        liabilitiesTotal.setText(liabilitiesMoney.getText());
        creditorrightsTotal = (TextView) view.findViewById(R.id.creditorrights_total);
        creditorrightsMoney = (TextView) view.findViewById(R.id.creditorrights_money);
        companyReimbursement = (TextView) view.findViewById(R.id.Company_reimbursement);
        creditorrightsMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "应收款项(CNY)"))) + "");
        companyReimbursement.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "公司报销(CNY)"))) + "");
        creditorrightsTotal.setText(Double.parseDouble(creditorrightsMoney.getText().toString()) + Double.parseDouble(companyReimbursement.getText().toString()) + "");
        investmentTotal = (TextView) view.findViewById(R.id.investment_total);
        investmentMoney = (TextView) view.findViewById(R.id.investment_money);
        investmentMoney1 = (TextView) view.findViewById(R.id.investment_1);
        investmentMoney2 = (TextView) view.findViewById(R.id.investment_2);
        investmentMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "基金账户(CNY)"))) + "");
        investmentMoney1.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "余额宝(CNY)"))) + "");
        investmentMoney2.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "股票账户(CNY)"))) + "");
        investmentTotal.setText(Double.parseDouble(investmentMoney.getText().toString()) + Double.parseDouble(investmentMoney1.getText().toString()) + Double.parseDouble(investmentMoney2.getText().toString()) + "");
        if(Double.parseDouble(creditTotal.getText().toString())<0){
            liabilities.setText(creditTotal.getText());
        }else{
            liabilities.setText("0");
        }

        assets.setText(Double.parseDouble(cashTotal.getText().toString().trim())+Double.parseDouble(financeTotal.getText().toString())+Double.parseDouble(fictitiousTotal.getText().toString())+"");
        net_assets.setText(Double.parseDouble(assets.getText().toString())+Double.parseDouble(creditTotal.getText().toString())+"");
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        currentTime = System.currentTimeMillis() + "";
        try {
            startTime = DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cash.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "现金(CNY)"))) + "");
        cashTotal.setText(cash.getText());
        financeCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "银行卡(CNY)"))) + "");
        financeTotal.setText(financeCardMoney.getText());
        busCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "公交卡(CNY)"))) + "");
        rideCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "饭卡(CNY)"))) + "");
        alipayMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "支付宝(CNY)"))) + "");
        fictitiousTotal.setText(Double.parseDouble(busCardMoney.getText().toString()) + Double.parseDouble(rideCardMoney.getText().toString()) + Double.parseDouble(alipayMoney.getText().toString()) + "");
        creditCardMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "信用卡(CNY)"))) + "");
        creditTotal.setText(creditCardMoney.getText());
        liabilitiesMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "应付款项(CNY)"))) + "");
        liabilitiesTotal.setText(liabilitiesMoney.getText());
        creditorrightsMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "应收款项(CNY)"))) + "");
        companyReimbursement.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "公司报销(CNY)"))) + "");
        creditorrightsTotal.setText(Double.parseDouble(creditorrightsMoney.getText().toString()) + Double.parseDouble(companyReimbursement.getText().toString()) + "");
        investmentMoney.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "基金账户(CNY)"))) + "");
        investmentMoney1.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "余额宝(CNY)"))) + "");
        investmentMoney2.setText(MathUtils.format(DataBaseUtils.sumMoneyInAndOut(DataBaseUtils.queryUseAccount(startTime, currentTime, "股票账户(CNY)"))) + "");
        investmentTotal.setText(Double.parseDouble(investmentMoney.getText().toString()) + Double.parseDouble(investmentMoney1.getText().toString()) + Double.parseDouble(investmentMoney2.getText().toString()) + "");
        if(Double.parseDouble(creditTotal.getText().toString())<0){
            liabilities.setText(creditTotal.getText());
        }else{
            liabilities.setText("0");
        }
        assets.setText(Double.parseDouble(cashTotal.getText().toString().trim())+Double.parseDouble(financeTotal.getText().toString())+Double.parseDouble(fictitiousTotal.getText().toString())+"");
        net_assets.setText(Double.parseDouble(assets.getText().toString())+Double.parseDouble(creditTotal.getText().toString())+"");
    }
}
