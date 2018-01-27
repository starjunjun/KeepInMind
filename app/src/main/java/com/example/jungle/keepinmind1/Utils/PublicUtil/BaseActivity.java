package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.os.Bundle;

public class BaseActivity extends AppCompatSwipeBack {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        ManagerUtils.addActivity(this);
        //将全部继承此activity的活动集中在ManagerUtils管理
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //若活动已经被销毁则无需重复回收，此时出栈
        ManagerUtils.removeActivity(this);
    }

}