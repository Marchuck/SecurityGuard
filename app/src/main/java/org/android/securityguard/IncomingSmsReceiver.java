package org.android.securityguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import org.android.securityguard.db.SmsReceivedPojo;

import java.util.Date;
import java.util.UUID;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class IncomingSmsReceiver extends BroadcastReceiver {


    public static final String TAG = IncomingSmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: SMS!!!!");
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        if (bundle != null) {

            Object array = bundle.get("pdus");
            if (array == null) return;

            final Object[] pdusObj = (Object[]) array;

            for (Object aPdusObj : pdusObj) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                String message = currentMessage.getDisplayMessageBody();

                Log.i("SmsReceiver", "senderNum: " + phoneNumber + "; message: " + message);

                SmsReceivedPojo pojo = new SmsReceivedPojo(
                        uuid(), message, phoneNumber, new Date().toString()
                );

                if (!InternetWatcher.available(context)) {
                    saveToDatabaseForFutureSending(pojo);
                } else {
                    postSmsToRemoteServer(pojo);
                }

            } // end for loop
        } // bundle is null
    }

    private void saveToDatabaseForFutureSending(SmsReceivedPojo pojo) {
        App.saveRecord(pojo);
    }

    private void postSmsToRemoteServer(SmsReceivedPojo pojo) {
        App.postSmsToRemoteServer(pojo);
    }

    private String uuid() {
        return UUID.randomUUID().toString();
    }
}
