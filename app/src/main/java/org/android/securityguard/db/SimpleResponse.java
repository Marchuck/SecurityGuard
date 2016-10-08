package org.android.securityguard.db;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class SimpleResponse {
    private String status;

    public SimpleResponse() {
    }

    public SimpleResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return status;
    }

    public boolean isOk() {
        return status != null && status.equals("OK");
    }
}
