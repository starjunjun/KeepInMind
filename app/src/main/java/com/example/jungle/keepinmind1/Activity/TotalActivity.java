package com.example.jungle.keepinmind1.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.jungle.keepinmind1.Adapter.ViewPagerAdapter;
import com.example.jungle.keepinmind1.Bean.RetrunJson;
import com.example.jungle.keepinmind1.Fragment.AccountFragment;
import com.example.jungle.keepinmind1.Fragment.DetailBillFragment;
import com.example.jungle.keepinmind1.Fragment.ManageMoneyFragment;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Service.DownloadService;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.MathUtils;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DatabaseDump;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DownFileUtil;
import com.example.jungle.keepinmind1.Utils.PublicUtil.PhotoDialog;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.HttpResultSubscriber;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.MyService;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.NetRequestFactory;
import com.example.jungle.keepinmind1.Utils.RetrofitUtil.Transform;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jxl.read.biff.BiffException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class TotalActivity extends AppCompatActivity implements View.OnClickListener {
    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private Toolbar toolbar;
    private android.support.design.widget.FloatingActionButton fab, fab_one, fab_two, fab_three;
    private CircleImageView icon_image;
    private NavigationView nav_view;
    private Uri photoUri1;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private DrawerLayout drawerLayout;
    private boolean isShow = false;
    private int fab_height;
    private CircleImageView icon_imagec;
    private TextView username;
    private TextView description;
    private Boolean chi;
    private Boolean eng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_total);
        //1、获取Preferences
        SharedPreferences settings = getSharedPreferences("data", 0);
        //2、取出数据
        MathUtils.budget = settings.getFloat("budget", 0);
        MathUtils.flags = settings.getBoolean("flag", false);
        MathUtils.account = settings.getString("account", "");
        MathUtils.head = settings.getString("head","");
        MathUtils.introduce = settings.getString("introduce","--");
        MathUtils.username = settings.getString("username","--");
        chi=settings.getBoolean("chi",false);
        eng=settings.getBoolean("eng",false);

        if (System.currentTimeMillis() - settings.getLong("signintime", 0) > 7 * 24 * 60 * 60 * 1000) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("account","");
            editor.putString("username","--");
            editor.putString("head","");
            editor.putString("introduce","--");
            editor.putBoolean("flags",false);
            MathUtils.account="";
            MathUtils.flags = false;
            editor.commit();
        }
        LitePal.getDatabase();
        if (!(chi&&eng)) {
            Intent intent = new Intent(this, DownloadService.class);
            startService(intent);
        }
        icon_image = (CircleImageView) findViewById(R.id.icon_image);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        icon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initNav();
        initViewPager();
        initView();
    }

    private void initNav() {
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.inOut:
                        Intent intent = new Intent(TotalActivity.this, ChartActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.ocr:
                        drawerLayout.closeDrawers();
                        new PhotoDialog(TotalActivity.this).show();
                        break;
                    case R.id.uploadBook:
                        if (!MathUtils.flags) {
                            Intent intent1 = new Intent(TotalActivity.this, SignInActivity.class);
                            startActivity(intent1);
                        } else {
                            DatabaseDump db = new DatabaseDump(LitePal.getDatabase(), "/sdcard/export.xml");
                            db.writeExcel("managemoneydbbean");
                            String FName = Environment.getExternalStorageDirectory() + "/managemoneydbbean.xls";

                            File file1 = new File(FName);
                            // create RequestBody instance from file
                            RequestBody description =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                            RequestBody username =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), MathUtils.account + ".xls");

                            // MultipartBody.Part is used to send also the actual file name
                            MultipartBody.Part file =
                                    MultipartBody.Part.createFormData("file", file1.getName(), description);


                            NetRequestFactory.getInstance().createService(MyService.class).upload(username, file).compose(Transform.<RetrunJson<String>>defaultSchedulers()).subscribe(new HttpResultSubscriber<RetrunJson<String>>() {

                                @Override
                                public void onSuccess(RetrunJson<String> rj) {
                                    System.out.println(rj.getResult().toString());
                                }

                                @Override
                                public void _onError(RetrunJson<String> rj) {

                                }

                            });
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.downloadBook:
                        if (!MathUtils.flags) {
                            Intent intent1 = new Intent(TotalActivity.this, SignInActivity.class);
                            startActivity(intent1);
                        } else {
                            NetRequestFactory.getInstance().createService(MyService.class).download(MathUtils.account + ".xls").compose(Transform.<ResponseBody>defaultSchedulers()).subscribe(new Subscriber<ResponseBody>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    boolean writtenToDisk = writeResponseBodyToDisk(responseBody, MathUtils.account + ".xls");
                                    DataSupport.deleteAll("managemoneydbbean");
                                    DatabaseDump db1 = new DatabaseDump(LitePal.getDatabase(), "/sdcard/export.xml");
                                    try {
                                        db1.readExcel(Environment.getExternalStorageDirectory() + File.separator + MathUtils.account + ".xls");
                                        EventBus.getDefault().post("同步");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (BiffException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.exportBook:
                        DatabaseDump db = new DatabaseDump(LitePal.getDatabase(), "/sdcard/export.xml");
                        db.writeExcel("managemoneydbbean");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.InBook:
                        drawerLayout.closeDrawers();
                        new LFilePicker()
                                .withActivity(TotalActivity.this)
                                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                                .withTitle("选择导入的文件")
                                .withMutilyMode(false)
                                .withFileFilter(new String[]{".xls"})
                                .withBackIcon(Constant.BACKICON_STYLETHREE)
                                .withBackgroundColor("#FFFFFF")
                                .withTitleColor("#000000")
                                .start();
                        break;
                    case R.id.voice:
                        drawerLayout.closeDrawers();
                        Intent intent3 = new Intent(TotalActivity.this, SpeechRecognitionActivity.class);
                        startActivity(intent3);
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
        });
        View headerView = nav_view.getHeaderView(0);
        icon_imagec = (CircleImageView) headerView.findViewById(R.id.icon_imagec);
        username = (TextView) headerView.findViewById(R.id.username);
        description = (TextView) headerView.findViewById(R.id.description);
        if(!MathUtils.head.equals("")){
            Glide.with(this).load(MathUtils.head).into(icon_imagec);
            Glide.with(this).load(MathUtils.head).into(icon_image);
        }
        if(!MathUtils.username.equals("--")){
            username.setText(MathUtils.username);
        }
        if(!MathUtils.introduce.equals("--")){
            description.setText(MathUtils.introduce);
        }
        icon_imagec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            System.out.println(Environment.getExternalStorageDirectory() + File.separator + filename);
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + File.separator + filename);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
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
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int height = v.getMeasuredHeight();
//        int space = getResources().getDimensionPixelOffset(R.dimen.dp_10);
        fab_height = fab_one.getMeasuredHeight();
        System.out.println(fab_height);
        int totalD = -height;
        switch (v.getId()) {
            case R.id.fab:
                if (!isShow) {
                    animationShow(fab_one, totalD);
                    animationShow(fab_two, 2 * totalD);
                    animationShow(fab_three, 3 * totalD);
                    isShow = true;
                } else {
                    animationHint(fab_one, totalD);
                    animationHint(fab_two, 2 * totalD);
                    animationHint(fab_three, 3 * totalD);
                    isShow = false;
                }
                break;
            case R.id.fab_one:
                if (isShow) {
                    animationHint(fab_one, totalD);
                    animationHint(fab_two, 2 * totalD);
                    animationHint(fab_three, 3 * totalD);
                    isShow = false;
                }
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_two:
                if (isShow) {
                    animationHint(fab_one, totalD);
                    animationHint(fab_two, 2 * totalD);
                    animationHint(fab_three, 3 * totalD);
                    isShow = false;
                }
                new PhotoDialog(TotalActivity.this).show();
                break;
            case R.id.fab_three:
                if (isShow) {
                    animationHint(fab_one, totalD);
                    animationHint(fab_two, 2 * totalD);
                    animationHint(fab_three, 3 * totalD);
                    isShow = false;
                }
                Intent intent1 = new Intent(this, SpeechRecognitionActivity.class);
                startActivity(intent1);
            default:
                if (isShow) {
                    animationHint(fab_one, totalD);
                    animationHint(fab_two, 2 * totalD);
                    animationHint(fab_three, 3 * totalD);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
                Toast.makeText(getApplicationContext(), "导入成功", Toast.LENGTH_SHORT).show();
                DataSupport.deleteAll("managemoneydbbean");
                DatabaseDump db1 = new DatabaseDump(LitePal.getDatabase(), "/sdcard/export.xml");
                try {
                    db1.readExcel(list.get(0).toString());
                    EventBus.getDefault().post("total");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
