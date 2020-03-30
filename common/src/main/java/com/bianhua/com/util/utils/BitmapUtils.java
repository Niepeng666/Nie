package com.bianhua.com.util.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 *
 * Created by Administrator on 2018/11/12.
 *
 */

public class BitmapUtils {
    public static Bitmap decodeSmpledBitmapFormResource(Resources resources, int resID, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources,resID,options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources,resID,options);
    }

    //返回采样率
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height>reqHeight || width >reqWidth){
            final int halfHeight =  height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight/inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
