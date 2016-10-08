package org.android.securityguard.db;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class SmsReceivedPojo {
    private String uuid;
    private String message;
    private String number;
    private String date;

    public SmsReceivedPojo() {
    }

    public SmsReceivedPojo(String uuid, String message, String number, String date) {
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

    public static SmsReceivedPojo valueOf(SmsReceived smsReceived) {
        return new SmsReceivedPojo(
                smsReceived.getUuid(),
                smsReceived.getMessage(),
                smsReceived.getNumber(),
                smsReceived.getDate()
        );
    }
}
