package com.example.jungle.keepinmind1.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jungle.keepinmind1.Interface.MyCallBack;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;
import com.example.jungle.keepinmind1.Utils.PublicUtil.OcrUtil;

import java.util.concurrent.ExecutionException;


public class PhotoActivity extends BaseActivity {
    private Uri photoUri1;


    private Bitmap bitmap1;
    private ImageView img1;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {         // handle message
            switch (msg.what) {
                case 1:
                    img1.setImageBitmap(bitmap1);

                    OcrUtil.ScanChinese(bitmap1, new MyCallBack() {
                        @Override
                        public void response(String result) {
                            Log.i("1111111111", "response: " + result);
                        }
                    });
            }

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("小票识别");
        }
        Intent i = getIntent();
        photoUri1 = i.getData();
        img1 = (ImageView) findViewById(R.id.img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bitmap1 = Glide.with(PhotoActivity.this)
                            .load(photoUri1)
                            .asBitmap()
                            .fitCenter().into(600, 600)
                            .get();
                    Message message = handler.obtainMessage(1);
                    handler.sendMessage(message);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
