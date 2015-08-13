package com.example.clement.emm_project2.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Clement on 13/08/15.
 */
public class App extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}
