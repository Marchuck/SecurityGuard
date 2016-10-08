package org.android.securityguard;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;


public class NetReceiver extends BroadcastReceiver {
    public static final String TAG = NetReceiver.class.getSimpleName();


    public NetReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: INTERNET CONNECTION");
        Context appContext = context.getApplicationContext();
        if (InternetWatcher.available(appContext)) {
            //internet is available
            App.bulkPostAllSmses();
        }
    }
}