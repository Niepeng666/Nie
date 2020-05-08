package com.common.com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Base64;

import com.common.com.util.LogUtil;
import com.common.com.util.LogUtils;


/**
 * 
 * @author hkrt
 * 					------------处理图片------------
 */
public class BitmapManage {
	
	//计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}

	/**
	 *
	 * @param bitmap
	 * @return  把bitmap转换成String
	 */
	public static String BitmapToString(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return android.util.Base64.encodeToString(appicon, Base64.DEFAULT);

	}
	/**
	 * string转成bitmap
	 *
	 * @param st
	 */
	public static Bitmap StringToBitmap(String st) {

		Bitmap bitmap = null;
		try
		{byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap =BitmapFactory.decodeByteArray(bitmapArray, 0,bitmapArray.length);
			return bitmap;
		}
		catch (Exception e)
		{return null;}
	}
	 /*
     * Drawable转化为Bitmap 
    */
	public  Bitmap drawableToBitmap(Drawable drawable) {  
	   
	    int width = drawable.getIntrinsicWidth();  
	    int height = drawable.getIntrinsicHeight();  
	    Bitmap bitmap = Bitmap.createBitmap(width, height,drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888: Config.RGB_565);
	    Canvas canvas = new Canvas(bitmap);  
	    drawable.setBounds(0,0,width,height);  
	    drawable.draw(canvas);  
	    
	    return bitmap;  
	 } 

	/**
	 * 尺寸压缩
	 * @param mContext
	 * @param imgPath
	 * @return
	 */
	public static String getCompressedImgPath(Context mContext, String imgPath) {
		File file = new File(imgPath);
		LogUtils.i("原图文件大小：" + file.length() / 1024 + "kb");
		int degree = readPictureDegree(imgPath);
		Bitmap bitmap = null;

		if (file.length() < 1024 * 100 && degree == 0) {
			return imgPath;
		}

		if (file.length() > 1024 * 100) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imgPath, options);

			if (options.outWidth > options.outHeight) {
				options.inSampleSize = calculateInSampleSize(options, 1280, 720);
			} else {
				options.inSampleSize = calculateInSampleSize(options, 720, 1280);
			}
			options.inJustDecodeBounds = false;

			bitmap = BitmapFactory.decodeFile(imgPath, options);
//            Logger.i("comparess", "比例压缩Bitmap大小：" + bitmap.getByteCount() / 1024 + "kb");
		}

		if (degree != 0) {
			bitmap = rotaingImageView(degree, bitmap);
//            Logger.i("comparess", "旋转角度Bitmap大小：" + bitmap.getByteCount() / 1024 + "kb");
		}

		bitmap = compsressImageByQuality(bitmap);
		LogUtil.i("comparess", "质量压缩后Bitmap大小：" + bitmap.getByteCount() / 1024 + "kb");
		String path = mContext.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg";
		try {
			FileOutputStream fileout = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileout);
			fileout.flush();
//            Logger.i("comparess", "最终文件大小：" + new File(path).length() / 1024 + "kb");
			return path;
		} catch (IOException e) {
			e.printStackTrace();
			System.gc();
			return "";
		}
	}
	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	private static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	private static Bitmap rotaingImageView(int degree, Bitmap bitmap) {
		//旋转图片 动作
		Matrix matrix = new Matrix();

		matrix.postRotate(degree);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 4.尺寸压缩（通过缩放图片像素来减少图片占用内存大小）
	 *
	 * @param bmp
	 * @param file
	 */

	public static void sizeCompress(Bitmap bmp, File file) {
		// 尺寸压缩倍数,值越大，图片尺寸越小
		int ratio = 8;
		// 压缩Bitmap到对应尺寸
		Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
		canvas.drawBitmap(bmp, null, rect, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 把压缩后的数据存放到baos中
		result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 质量压缩方法
	 *
	 * @param image
	 * @return
	 */
	private static Bitmap compsressImageByQuality(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		image.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 100 && options > 0) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;
//            Logger.i("comparess", "质量压缩一次：" + baos.toByteArray().length / 1024 + "kb");
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		return BitmapFactory.decodeStream(isBm, null, null);
	}


}
