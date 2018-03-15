package com.example.pc.loginconfacebook;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by PC on 13/03/2018.
 */
//clase que podra ser accesible desde cualquier lugar. es una buena practica.
public class facebookApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //inicializa el sdk de facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
