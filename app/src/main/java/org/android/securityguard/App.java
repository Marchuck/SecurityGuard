package org.android.securityguard;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.android.securityguard.db.SimpleResponse;
import org.android.securityguard.db.SmsReceived;
import org.android.securityguard.db.SmsReceivedPojo;
import org.android.securityguard.db.SmsesCollection;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.RetrofitError;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    private static Context ctx;

    private PostSmsAPI postSmsAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this.getApplicationContext();
        postSmsAPI = new PostSmsAPI();
    }

    public static void saveRecord(final SmsReceivedPojo pojo) {
        Log.d(TAG, "saveRecord: ");
        Realm realm = Realm.getInstance(ctx);
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Log.d(TAG, "execute: ");
                    SmsReceived smsReceived = new SmsReceived(
                            pojo.getUuid(),
                            pojo.getMessage(),
                            pojo.getNumber(),
                            pojo.getDate()
                    );
                    realm.copyToRealmOrUpdate(smsReceived);
                }
            });
        } catch (Exception x) {
            Log.e(TAG, "saveRecord: ", x);
        }

    }

    public static void postSmsToRemoteServer(final SmsReceivedPojo pojo) {
        Log.d(TAG, "postSmsToRemoteServer: ");

        PostSmsAPI api = getPostApi();

        api.getApi().postSms(pojo).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<SimpleResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        saveRecord(pojo);
                    }

                    @Override
                    public void onNext(SimpleResponse simpleResponse) {
                        Log.i(TAG, "onNext: " + simpleResponse);
                    }
                });
    }

    public static void bulkPostAllSmses() {
        Log.d(TAG, "bulkPostAllSmses: ");
        List<SmsReceivedPojo> pojos = new ArrayList<>();
        try {

            final Realm realm = Realm.getInstance(ctx);
            RealmResults<SmsReceived> smses = realm.where(SmsReceived.class).findAll();
            if (smses == null || smses.size() == 0) return;

            for (int i = 0; i < smses.size(); i++) {
                pojos.add(SmsReceivedPojo.valueOf(smses.get(i)));
            }

            PostSmsAPI api = getPostApi();

            api.getApi()
                    .postBatchSmses(new SmsesCollection(pojos))
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<SimpleResponse>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: failed to post bulky post", e);
                            if (e instanceof RetrofitError) {
                                RetrofitError err = ((RetrofitError) e);
                                Log.w(TAG, "url: " + err.getUrl());
                                Log.w(TAG, "onError: " + err.getKind().name());
                            }
                        }

                        @Override
                        public void onNext(SimpleResponse simpleResponse) {
                            Log.d(TAG, "onNext: ");
                            if (simpleResponse != null && simpleResponse.isOk()) {
                                clearTable();
                            }
                        }

                        private void clearTable() {
                            Log.d(TAG, "clearTable: ");
                            Realm realm = Realm.getInstance(ctx);
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Log.i(TAG, "execute: clearing database");
                                    realm.clear(SmsReceived.class);
                                }
                            });
                        }
                    });


        } catch (Exception x) {
            Log.e(TAG, "bulkPostAllSmses: ", x);
        }
    }

    public static PostSmsAPI getPostApi() {
        return ((App) ctx).postSmsAPI;
    }
}
