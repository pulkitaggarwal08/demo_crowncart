package com.innovative.crownkart.config;

import android.app.Application;

import com.innovative.crownkart.api.ApiHelper;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class App extends Application {
    private static App instance;
    private static ApiHelper apiHelper;
    private static AppPreference appPreference;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiHelper = ApiHelper.init(instance);
    }

    public static App getAppContext() {
        return instance;
    }

    public static ApiHelper getApiHelper(){
        return apiHelper;
    }

    public static AppPreference getAppPreference(){
        return appPreference;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
