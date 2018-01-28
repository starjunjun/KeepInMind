package com.example.jungle.keepinmind1.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jungle.keepinmind1.Adapter.ManageMoneyAdapter;
import com.example.jungle.keepinmind1.Bean.GuPiaoBean;
import com.example.jungle.keepinmind1.Bean.GuPiaoContent;
import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;
import com.example.jungle.keepinmind1.Utils.PublicUtil.RecycleViewDivider;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GuPiaoContentActivity extends BaseActivity {
    private String searchCode;
    private Toolbar toolbar;
    private TextView name_code;
    private TextView state;
    private TextView trade;
    private TextView pricechange;
    private TextView changepercent;
    private TextView open;
    private TextView settlement;
    private TextView volume;
    private TextView amount;
    private TextView high;
    private TextView low;
    private TextView limitUp;
    private TextView limitDown;
    private TextView in;
    private TextView out;
    private TextView committe;
    private TextView liangbi;
    private TextView currency;
    private TextView total;
    private TextView earnings;
    private TextView shijinglv;
    private TextView EarningsPerShare;
    private TextView AssetValuePerShare;
    private TextView content;
    private TextView TotalCapitalStock;
    private TextView FlowOfEquity;
    private ImageView search_button;
    private EditText search_et;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gu_piao_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("股票详情");
        }
        findId();
        initGuPiao();

    }

    private void findId() {
        Intent intent =getIntent();
        searchCode= intent.getStringExtra("searchCode");
        System.out.println(searchCode);
        name_code = (TextView) findViewById(R.id.name_code);
        state = (TextView) findViewById(R.id.state);
        trade = (TextView) findViewById(R.id.trade);
        pricechange = (TextView) findViewById(R.id.pricechange);
        changepercent = (TextView) findViewById(R.id.changepercent);
        open = (TextView) findViewById(R.id.open);
        settlement = (TextView) findViewById(R.id.settlement);
        volume = (TextView) findViewById(R.id.volume);
        amount = (TextView) findViewById(R.id.amount);
        high = (TextView) findViewById(R.id.high);
        low = (TextView) findViewById(R.id.low);
        limitUp = (TextView) findViewById(R.id.limitUp);
        limitDown = (TextView) findViewById( R.id.limitDown);
        in = (TextView) findViewById(R.id.in);
        out = (TextView) findViewById(R.id.out);
        committe = (TextView) findViewById(R.id.committe);
        liangbi = (TextView) findViewById(R.id.liangbi);
        currency = (TextView) findViewById(R.id.currency);
        total = (TextView) findViewById(R.id.total);
        earnings = (TextView) findViewById(R.id.earnings);
        shijinglv = (TextView) findViewById(R.id.shijinglv);
        EarningsPerShare = (TextView) findViewById(R.id.EarningsPerShare);
        AssetValuePerShare = (TextView) findViewById(R.id.AssetValuePerShare);
        content= (TextView) findViewById(R.id.content);
        TotalCapitalStock = (TextView) findViewById(R.id.TotalCapitalStock);
        FlowOfEquity = (TextView) findViewById(R.id.FlowOfEquity);
        search_button = (ImageView) findViewById(R.id.search_button);
        search_et = (EditText) findViewById(R.id.search_et);
        scroll = (ScrollView) findViewById(R.id.scroll);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCode = search_et.getText().toString().trim();
                initGuPiao();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

            }
        });


    }

    private void initGuPiao() {
        NetRequestFactory.getInstance().createService(MyService.class).getGuPiaoByCode(searchCode).compose(Transform.<RetrunJson<GuPiaoContent>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<GuPiaoContent>>() {
            @Override
            public void onSuccess(RetrunJson<GuPiaoContent> rj) {
                name_code.setText(rj.getResult().getName_code());
                state.setText(rj.getResult().getState());
                trade.setText(rj.getResult().getTrade());
                content.setText(rj.getResult().getContent());
                pricechange.setText(rj.getResult().getPricechange());
                changepercent.setText(rj.getResult().getChangepercent());
                open.setText(rj.getResult().getOpen());
                settlement.setText(rj.getResult().getSettlement());
                volume.setText(rj.getResult().getVolume());
                amount.setText(rj.getResult().getAmount());
                high.setText(rj.getResult().getHigh());
                low.setText(rj.getResult().getLow());
                limitUp.setText(rj.getResult().getLimitUp());
                limitDown.setText(rj.getResult().getLimitDown());
                in.setText(rj.getResult().getIn());
                out.setText(rj.getResult().getOut());
                committe.setText(rj.getResult().getCommitte());
                liangbi.setText(rj.getResult().getLiangbi());
                currency.setText(rj.getResult().getCurrency());
                total.setText(rj.getResult().getTotal());
                earnings.setText(rj.getResult().getEarnings());
                shijinglv.setText(rj.getResult().getShijinglv());
                EarningsPerShare.setText(rj.getResult().getEarningsPerShare());
                AssetValuePerShare.setText(rj.getResult().getAssetValuePerShare());
                FlowOfEquity.setText(rj.getResult().getFlowOfEquity());
                TotalCapitalStock.setText(rj.getResult().getTotalCapitalStock());
                scroll.scrollTo(0,0);
            }

        @Override
        public void _onError(RetrunJson<GuPiaoContent> rj) {

        }

    });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }

        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    @Override
    public void onBackPressed() {
        scrollToFinishActivity();//左滑退出activity
    }

}
