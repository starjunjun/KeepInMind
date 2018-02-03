package com.example.jungle.keepinmind1.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.jungle.keepinmind1.Adapter.ViewPagerAdapter;
import com.example.jungle.keepinmind1.Fragment.AccountFragment;
import com.example.jungle.keepinmind1.Fragment.DetailBillFragment;
import com.example.jungle.keepinmind1.Fragment.ManageMoneyFragment;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Service.DownloadService;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DatabaseDump;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DownFileUtil;
import com.example.jungle.keepinmind1.Utils.PublicUtil.PhotoDialog;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jxl.read.biff.BiffException;

import static android.os.Environment.getExternalStorageState;

public class TotalActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private android.support.design.widget.FloatingActionButton fab, fab_one, fab_two, fab_three;
    private CircleImageView icon_image;
    private NavigationView nav_view;
    private Uri photoUri1;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
private DrawerLayout drawerLayout;
    private boolean isShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_total);
        //1、获取Preferences
        SharedPreferences settings = getSharedPreferences("data", 0);
//2、取出数据
        MathUtils.budget = settings.getFloat("budget", 0);
        LitePal.getDatabase();
        icon_image = (CircleImageView) findViewById(R.id.icon_image);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        icon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setItemIconTintList(null);
        if (!(DownFileUtil.CheckExistFile(Environment.getExternalStorageDirectory() + "/Download/tessdata/chi") && DownFileUtil.CheckExistFile(Environment.getExternalStorageDirectory() + "/Download/tessdata/eng"))) {
            Intent intent = new Intent(this, DownloadService.class);
            startService(intent);
        }
        initViewPager();
        initView();

    }

    private void initViewPager() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> viewList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("流水");
        titleList.add("账户");
        titleList.add("理财");
        viewList.add(new DetailBillFragment(this));
        viewList.add(new AccountFragment());
        viewList.add(new ManageMoneyFragment());

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), viewList, titleList);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
    }

    private void initView() {
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab_one = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab_one);
        fab_two = (FloatingActionButton) findViewById(R.id.fab_two);
        fab_three = (FloatingActionButton) findViewById(R.id.fab_three);

        fab.setOnClickListener(this);
        fab_one.setOnClickListener(this);
        fab_two.setOnClickListener(this);
        fab_three.setOnClickListener(this);
    }

    private void animationShow(View view, int translateY) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, translateY),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f));
        set.setInterpolator(new OvershootInterpolator());
        set.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (!isShow) {
                    animationShow(fab_one, -150);
                    animationShow(fab_two, -300);
                    animationShow(fab_three, -450);
                    isShow = true;
                } else {
                    animationHint(fab_one, -150);
                    animationHint(fab_two, -300);
                    animationHint(fab_three, -450);
                    isShow = false;
                }
                break;
            case R.id.fab_one:
                if (isShow) {
                    animationHint(fab_one, -150);
                    animationHint(fab_two, -300);
                    animationHint(fab_three, -450);
                    isShow = false;
                }
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_two:
                if (isShow) {
                    animationHint(fab_one, -150);
                    animationHint(fab_two, -300);
                    animationHint(fab_three, -450);
                    isShow = false;
                }
                new PhotoDialog(TotalActivity.this).show();
                break;
            case R.id.fab_three:
                if (isShow) {
                    animationHint(fab_one, -150);
                    animationHint(fab_two, -300);
                    animationHint(fab_three, -450);
                    isShow = false;
                }
                Intent intent1 = new Intent(this, SpeechRecognitionActivity.class);
                startActivity(intent1);
            default:
                if (isShow) {
                    animationHint(fab_one, -150);
                    animationHint(fab_two, -300);
                    animationHint(fab_three, -450);
                    isShow = false;
                }
                break;
        }
    }

    private void animationHint(View view, int translateY) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationY", translateY, 0),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setInterpolator(new AnticipateInterpolator());
        set.start();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
// 得到图片的全路径

                photoUri1 = data.getData();
                Intent intent1 = new Intent(TotalActivity.this, PhotoActivity.class);
                intent1.setData(photoUri1);
                startActivity(intent1);

            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                if (resultCode == -1) {

                    Intent intent1 = new Intent(TotalActivity.this, PhotoActivity.class);
                    intent1.setData(photoUri1);
                    startActivity(intent1);
                }
            } else {
                Toast.makeText(TotalActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT)
                        .show();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inOut:
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.ocr:
                drawerLayout.closeDrawers();
                new PhotoDialog(TotalActivity.this).show();
                break;
            case R.id.uploadBook:
                DatabaseDump db = new DatabaseDump(LitePal.getDatabase(),"/sdcard/export.xml");
                db.writeExcel("managemoneydbbean");
                drawerLayout.closeDrawers();
                break;
            case R.id.InBook:
                DataSupport.deleteAll("managemoneydbbean");
                DatabaseDump db1 = new DatabaseDump(LitePal.getDatabase(),"/sdcard/export.xml");
                try {
                    db1.readExcel("/sdcard/managemoneydbbean.xls");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.voice:
                drawerLayout.closeDrawers();
                Intent intent1 = new Intent(this, SpeechRecognitionActivity.class);
                startActivity(intent1);
                break;
            case R.id.settings:
                drawerLayout.closeDrawers();
                break;
            case R.id.clearCache:
                drawerLayout.closeDrawers();
                break;
            case R.id.signOut:
                drawerLayout.closeDrawers();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
