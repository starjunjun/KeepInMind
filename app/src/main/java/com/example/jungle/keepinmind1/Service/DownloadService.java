package com.example.jungle.keepinmind1.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jungle.keepinmind1.Activity.TotalActivity;
import com.example.jungle.keepinmind1.R;
import com.example.jungle.keepinmind1.Utils.PublicUtil.DownFileUtil;

/**
 * Created by jungle on 2017/12/20.
 */

public class DownloadService extends IntentService {
    final String dirName = Environment.getExternalStorageDirectory() + "/Download/tessdata";
    private static String downloadUrl = "https://github.com/tesseract-ocr/tessdata/raw/master/chi_sim.traineddata";
    private static String downloadUrl1 = "https://github.com/tesseract-ocr/tessdata/raw/master/eng.traineddata";


    NotificationManager manager;
    int notification_id;

    public DownloadService() {
        super("DownloadServiceThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences settings = getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = settings.edit();
        if (!settings.getBoolean("chi", false)) {
            DownFileUtil.download("chi", downloadUrl);
            editor.putBoolean("chi", true);
        }
        if (!settings.getBoolean("eng", false)) {
            DownFileUtil.download("eng", downloadUrl1);
            editor.putBoolean("eng", true);
        }
        if (settings.getBoolean("chi", false) && settings.getBoolean("eng", false)) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(getApplicationContext());
            builder.setSmallIcon(R.drawable.textimage);
            builder.setTicker("随心记");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("随心记");
            builder.setContentText("OCR功能更新完毕,可正常使用。");
                /*builder.setDefaults(Notification.DEFAULT_SOUND);//设置声音
                builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
                builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动*/
            builder.setDefaults(Notification.DEFAULT_ALL);//设置全部

            Notification notification = builder.build();//4.1以上用.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
            manager.notify(notification_id, notification);
        }

    }
}