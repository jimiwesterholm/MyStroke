package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.JSONtoSQLite;
import com.example.jimi.mystroke.models.User;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 25/12/2017.
 */

public class SynchDatabaseTask implements Runnable {
    private String[] classNames = {"assessment", "exercise", "imagery", "user", "therapist", "patient", "patient_assessess_exercise", "patient_list_exercise", "patient_list_imagery", "comment",};
    private Context context;

    @Override
    public void run() {
        User loggedInUser = Globals.getInstance().getUser();
        User user = findUser(loggedInUser.getUid());
        if(user == null || user.getPassword() != loggedInUser.getPassword() || user.getSalt() != loggedInUser.getSalt()) {
            //TODO: Consider additional security measures? - esp. server side authentication
            return;
        }
        boolean finished = false;
        while (!finished) {
            for (String className : classNames) {
                //TODO: get changes, upload
                FetchRecordsTask frtEx = new FetchRecordsTask();
                frtEx.setClassName(className);
                frtEx.setContext(context);
                frtEx.call();
            }

        }
        /*

         */
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private User findUser(int id) {
        return null;
    }
}
