package com.ups.pandorasbox.tensorflowclient.nonactivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hgg0qfv on 6/29/2017.
 */

public class Utility
{
    // THIS METHOD CONVERTS AN IMAGE ON DISK INTO A BASE64 STRING
    public static String encodeImageToBase64(String imagePath, Activity activity)
    {
        // Check for permissions and acquire if necessary
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        // Convert the bitmap image into a Base64 encoding
        Bitmap b = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        // Return the Base64 encoded string
        return imageString;
    }

    // THIS METHOD CONVERTS AN IMAGE IN MEMORY (BITMAP) INTO A BASE64 STRING
    public static String encodeImageToBase64(Bitmap imageBitmap, Activity activity)
    {
        // Convert the bitmap image into a Base64 encoding
        Bitmap b = imageBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        // Return the Base64 encoded string
        return imageString;
    }

    // THIS METHOD CONVERTS A BASE64 STRING IN MEMORY INTO AN IMAGE ON DISK
    public static void decodeImageFromBase64(String imageString, String destination, Activity activity)
    {
        try
        {
            // Check for permissions and acquire if necessary
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }

            // Decode the Base64 string into bytecode and save as an image on disk
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap b = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            FileOutputStream fos = new FileOutputStream(destination);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
