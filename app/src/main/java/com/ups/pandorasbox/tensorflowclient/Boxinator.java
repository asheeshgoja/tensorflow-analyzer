package com.ups.pandorasbox.tensorflowclient;

import android.app.Application;
import android.content.Context;

/**
 * Created by hgg0qfv on 6/29/2017.
 */

public class Boxinator extends Application
{
    private static Context context;

    public void onCreate()
    {
        super.onCreate();
        Boxinator.context = getApplicationContext();
    }

    public static Context getAppContext()
    {
        return Boxinator.context;
    }
}
