package org.android.securityguard;

import com.google.gson.Gson;

import org.android.securityguard.db.SimpleResponse;
import org.android.securityguard.db.SmsReceivedPojo;
import org.android.securityguard.db.SmsesCollection;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.POST;


/**
 * Project "SecurityGuard"
 * <p>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class PostSmsAPI {

    private LocalAPI api;

    public interface LocalAPI {
        String endpoint = "http://192.168.1.6:8080";

        @POST("/single/")
        rx.Observable<SimpleResponse> postSms(@Body SmsReceivedPojo sms);

        @POST("/bulk/")
        rx.Observable<SimpleResponse> postBatchSmses(@Body SmsesCollection collection);

    }

    public LocalAPI getApi() {
        if (api == null) api = new RestAdapter.Builder()
                .setEndpoint(LocalAPI.endpoint)
                .setConverter(new GsonConverter(new Gson()))
                .build()
                .create(LocalAPI.class);
        return api;
    }
}
