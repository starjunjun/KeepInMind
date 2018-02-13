package com.example.jungle.keepinmind1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.Bean.User;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignInActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView R_textview;
    private com.rengwuxian.materialedittext.MaterialEditText username;
    private com.rengwuxian.materialedittext.MaterialEditText password;
    private Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("请先登录");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();


    }

    private void initView() {

        R_textview = (TextView) findViewById(R.id.R_textview);
        R_textview.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        R_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        username = (MaterialEditText) findViewById(R.id.username);
        password = (MaterialEditText) findViewById(R.id.password);
        SignIn = (Button) findViewById(R.id.SignIn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText() != null && password.getText() != null) {
                    System.out.println("111");
                    NetRequestFactory.getInstance().createService(MyService.class).sign(username.getText().toString().trim(),password.getText().toString().trim()).compose(Transform.<RetrunJson<User>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<User>>() {
                        @Override
                        public void onSuccess(RetrunJson<User> rj) {
                            if(rj.getResultcode().equals("200")){
                                System.out.println(rj.getResult().toString());
                                SharedPreferences settings = getSharedPreferences("data", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("username",rj.getResult().getUsername());
                                editor.putString("account",rj.getResult().getAccount());
                                editor.putLong("signintime",System.currentTimeMillis());
                                MathUtils.account=rj.getResult().getAccount();
                                editor.putBoolean("flag",true);
                                MathUtils.flags=true;
                                editor.commit();
                                finish();

//
                            }

                        }

                        @Override
                        public void _onError(RetrunJson<User> rj) {

                        }

                    });



                } else {
                    System.out.println("账号密码为空");
                }
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
