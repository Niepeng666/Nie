package com.common.com.util.save_picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Save_tupian {

    private static File file;

    public static void  baocun(Bitmap photo,Context context) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");//“zing_image”是文件名
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";           //zxing_image.png 是图片...
        file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));

    }
}
