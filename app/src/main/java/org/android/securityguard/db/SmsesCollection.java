package org.android.securityguard.db;

import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

/**
 * Project "SecurityGuard"
 * <p/>
 * Created by Lukasz Marczak
 * on 08.10.16.
 */
public class SmsesCollection {
    private List<SmsReceivedPojo> smses = new ArrayList<>();

    public SmsesCollection(List<SmsReceivedPojo> smses) {
        this.smses = smses;
    }

    public SmsesCollection() {
    }

    public List<SmsReceivedPojo> getSmses() {
        return smses;
    }

    public void setSmses(List<SmsReceivedPojo> smses) {
        this.smses = smses;
    }
}
