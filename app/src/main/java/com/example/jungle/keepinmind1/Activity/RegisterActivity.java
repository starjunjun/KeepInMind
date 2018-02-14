package com.example.jungle.keepinmind1.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.BaseActivity;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends BaseActivity {
    private Toolbar toolbar;
    private com.rengwuxian.materialedittext.MaterialEditText account;
    private com.rengwuxian.materialedittext.MaterialEditText username;
    private com.rengwuxian.materialedittext.MaterialEditText confirmPassword;
    private com.rengwuxian.materialedittext.MaterialEditText password;
    private Button Register;
    private CardView cardview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("注册页面");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    private void initView() {
        System.out.println("initView");
        account = (MaterialEditText) findViewById(R.id.account);
        username = (MaterialEditText) findViewById(R.id.username);
        password = (MaterialEditText) findViewById(R.id.password);
        confirmPassword = (MaterialEditText) findViewById(R.id.confirmPassword);
        Register = (Button) findViewById(R.id.Register);
        cardview2= (CardView) findViewById(R.id.cardview2);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button");
                if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "两次密码不一样", Toast.LENGTH_SHORT);
                } else if (password.getText() == null || confirmPassword.getText() == null || account.getText() == null || username.getText() == null) {
                    Toast.makeText(RegisterActivity.this, "还未填写完毕", Toast.LENGTH_SHORT);
                } else {
                    NetRequestFactory.getInstance().createService(MyService.class).register(username.getText().toString().trim(), account.getText().toString().trim(),password.getText().toString().trim()).compose(Transform.<RetrunJson<String>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<String>>() {
                        @Override
                        public void onSuccess(RetrunJson<String> rj) {
                            if(rj.getResultcode().equals("200")){
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT);
                            }
                            finish();
                        }

                        @Override
                        public void _onError(RetrunJson<String> rj) {

                        }

                    });
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
