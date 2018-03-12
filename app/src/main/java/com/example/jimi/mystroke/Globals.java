package com.example.jimi.mystroke;

import com.example.jimi.mystroke.models.User;

import java.sql.Timestamp;

/**
 * Created by jimi on 25/12/2017.
 */

public class Globals {
    private static final Globals ourInstance = new Globals();
    private User user;
    private int isPatient;
    private long latestUpdate;
    private final String dbUrl = "http://11cb102e.ngrok.io/api.php/";

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

    public int isPatient() {
        return isPatient;
    }

    public void setPatient(int patient) {
        isPatient = patient;
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
