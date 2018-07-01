package com.summer_course.utils;

import android.graphics.Bitmap;
import android.util.Log;
import com.summer_course.tasks.RetrieveBitmapTask;
import java.util.concurrent.ExecutionException;

/**
 * @author George Cristian
 *
 * Class that holds utility static functions for handling Facebook operations.
 *
 */
public class FacebookUtils {

    public static String TAG = FacebookUtils.class.getSimpleName();

    /**
     * Launches a RetrieveBitmapTask and waits for it's completion
     *
     * @param userID The Facebook ID of the current user
     * @return The profile pic of the current user as Bitmap
     */
    public static Bitmap getFacebookProfilePic(String userID) {
        Bitmap bitmapPic = null;

        try {
            bitmapPic = new RetrieveBitmapTask(userID).execute(userID).get();
        } catch (InterruptedException e) {
            Log.e(TAG, "Downloading picture was interrupted");
        } catch (ExecutionException e) {
            Log.e(TAG, "Downloading picture was interrupted");
        }

        return bitmapPic;
    }

}
