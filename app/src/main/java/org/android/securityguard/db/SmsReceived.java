package org.android.securityguard.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class SmsReceived extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String message;
    private String number;
    private String date;

    public SmsReceived() {
    }

    public SmsReceived(String uuid, String message, String number, String date) {
        this.uuid = uuid;
        this.message = message;
        this.number = number;
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
