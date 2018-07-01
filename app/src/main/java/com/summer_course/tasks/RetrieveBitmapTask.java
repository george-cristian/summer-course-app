package com.summer_course.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import com.summer_course.utils.FacebookUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author George Cristian
 *
 * Asynchronous task that retrieves the users profile picture in Bitmap format.
 *
 */
public class RetrieveBitmapTask extends AsyncTask<String, Void, Bitmap> {


    public RetrieveBitmapTask(String userID) {
    }

    @Override
    protected Bitmap doInBackground(String... userID) {
        Bitmap profilePic = null;
        try {
            URL imageURL = new URL("https://graph.facebook.com/" + userID[0] + "/picture?type=large");
            profilePic = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            Log.e(FacebookUtils.class.getSimpleName(), "Bad URL when downloading facebook pic");
        } catch (IOException e) {
            Log.e(FacebookUtils.class.getSimpleName(), "Error at downloading image");
        }

        return profilePic;
    }
}
