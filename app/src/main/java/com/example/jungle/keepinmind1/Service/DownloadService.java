package com.example.jungle.keepinmind1.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.jungle.keepinmind1.Utils.PublicUtil.DownFileUtil;

/**
 * Created by jungle on 2017/12/20.
 */

public class DownloadService extends IntentService {
    final String dirName = Environment.getExternalStorageDirectory() + "/Download/tessdata";
    private static String downloadUrl = "https://github.com/tesseract-ocr/tessdata/raw/master/chi_sim.traineddata";
    private static String downloadUrl1 = "https://github.com/tesseract-ocr/tessdata/raw/master/eng.traineddata";



    public DownloadService() {
        super("DownloadServiceThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DownFileUtil.download("chi", downloadUrl);
        DownFileUtil.download("eng", downloadUrl1);
}
}