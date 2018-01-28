package com.example.jungle.keepinmind1.Utils.PublicUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungle.keepinmind1.Activity.PhotoActivity;
import com.example.jungle.keepinmind1.Activity.TotalActivity;
import com.example.jungle.keepinmind1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jungle on 2018/1/27.
 */

public class PhotoDialog extends Dialog {
    private Activity mActivity;
    private TextView photo;
    private TextView take;
    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private Uri photoUri1;

    public PhotoDialog(Activity activity) {
        super(activity, R.style.MyDialog);
        this.mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photodialog);
        init();
        setViewLocation();
        setCanceledOnTouchOutside(true);
    }

    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels * 4 / 5;
        lp.height = dm.heightPixels * 1 / 4;
        // 设置dialog宽度为屏幕的4/5
        onWindowAttributesChanged(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
    }

    private void init() {
        photo = (TextView) findViewById(R.id.photo);
        take = (TextView) findViewById(R.id.take);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
// 判断存储卡是否可以用，可用进行存储
                if (hasSdcard()) {
                    SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                            "yyyy_MM_dd_HH_mm_ss");
                    String filename = timeStampFormat.format(new Date());
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, filename);
                    photoUri1 = mActivity.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri1);
                }
                mActivity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        });

    }

    private boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


}
