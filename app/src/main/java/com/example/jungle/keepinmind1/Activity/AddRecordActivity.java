package com.example.jungle.keepinmind1.Activity;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jungle.keepinmind1.Adapter.ViewPagerAdapter;
import com.example.jungle.keepinmind1.Fragment.AccountFragment;
import com.example.jungle.keepinmind1.Fragment.BalanceFragment;
import com.example.jungle.keepinmind1.Fragment.DetailBillFragment;
import com.example.jungle.keepinmind1.Fragment.InFragment;
import com.example.jungle.keepinmind1.Fragment.ManageMoneyFragment;
import com.example.jungle.keepinmind1.Fragment.OutFragment;
import com.example.jungle.keepinmind1.Fragment.TransferAccountFragment;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Service.DownloadService;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DownFileUtil;

import java.util.ArrayList;
import java.util.List;

public class AddRecordActivity extends BaseActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initView();
        initViewPager();

    }

    private void initView() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("记账");
        }
    }

    private void initViewPager() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> viewList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("收入");
        titleList.add("支出");
        titleList.add("转账");
        titleList.add("余额");
        viewList.add(new InFragment(this));
        viewList.add(new OutFragment(this));
        viewList.add(new TransferAccountFragment(this));
        viewList.add(new BalanceFragment(this));

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), viewList, titleList);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mViewPagerAdapter);
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
