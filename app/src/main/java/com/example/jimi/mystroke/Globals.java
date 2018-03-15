package com.example.jimi.mystroke;

import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;

/**
 * Created by jimi on 25/12/2017.
 */

public class Globals {
    private static final Globals ourInstance = new Globals();
    private User user;
    private int isLoggedAsPatient;
    private long latestUpdate;
    private final String dbUrl = "http://eae4c12d.ngrok.io/api.php/";

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

    public int isLoggedAsPatient() {
        return isLoggedAsPatient;
    }

    public void setLoggedAsPatient(int patient) {
        isLoggedAsPatient = patient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(long latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getDbUrl() {
        return dbUrl;
    }
}
