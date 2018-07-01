package com.summer_course.utils;

import android.graphics.Bitmap;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

/**
 * @author George Cristian
 *
 * Class that holds static functions that are useful for Bitmap operations.
 *
 */
public class BitmapUtils {

    /**
     * Converts a bitmap object into a String object.
     *
     * @param bitmap The source image as Bitmap
     * @return The String representation of the Bitmap
     */
    public static String getStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        String bitmapString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return bitmapString;
    }

}
