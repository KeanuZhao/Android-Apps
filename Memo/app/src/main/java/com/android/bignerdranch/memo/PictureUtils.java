package com.android.bignerdranch.memo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;


/**
 * Created by KeanuZhao on 2017/5/1.
 */

public class PictureUtils  {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
// Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
// Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
// Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }

    public static SpannableString bitmapConvert(Bitmap bitmap, int judge) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        Drawable drawable = (Drawable) bd;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);

        if(judge==1){
            SpannableString spannablespan1 = new SpannableString("[picture]");
            //☆
            spannablespan1.setSpan(span, 0, "[picture]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannablespan1;
        }else{
            SpannableString spannablespan2 = new SpannableString("[paint]");
            //☆
            spannablespan2.setSpan(span, 0, "[paint]".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannablespan2;
        }

    }


}
