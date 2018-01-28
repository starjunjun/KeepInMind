package com.example.jungle.keepinmind1.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;

public class PassageActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView passage_title;
    private TextView passage_content;
    private ImageView passage_imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        passage_content = (TextView) findViewById(R.id.passage_content);
        passage_title = (TextView) findViewById(R.id.passage_title);
        passage_imageview = (ImageView) findViewById(R.id.passage_img);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("详 文");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);//设置收缩后Toolbar上字体的颜色
        Intent intent =getIntent();
        Glide.with(PassageActivity.this).load(intent.getStringExtra("Img")).into(passage_imageview);

        passage_title.setText(intent.getStringExtra("Title"));
        passage_content.setText(intent.getStringExtra("Content").replace(" ","\n"));

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
