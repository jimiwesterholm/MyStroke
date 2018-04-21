package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.ExerciseImage;
import com.example.jimi.mystroke.models.User;

import java.util.List;

/**
 * Created by jimi on 25/12/2017.
 */

public class SyncDatabaseTask implements Runnable {
    private Context context;

    public SyncDatabaseTask(Context context) {
        this.context = context;
    }

    //TODO make run intermittently!!!
    @Override
    public void run() {
        String[] mediaClassNames = Globals.getInstance().getMediaClassNames();
        String[] classNames = Globals.getInstance().getClassNames();
        User loggedInUser = Globals.getInstance().getUser();
        User user = findUser(loggedInUser.getUsername());
        if(user == null || !user.getPassword().equals(loggedInUser.getPassword()) || !user.getSalt().equals(loggedInUser.getSalt())) {
            //TODO: Add security measures - esp. server side authentication
            return;
        }

        for (String className : classNames) {
            List<? extends DatabaseObject> databaseObjects = new GetChangedRecords(context).getChanged(className);
            if(databaseObjects.size() != 0) {
                SendRecordsTask sendRecordsTask = new SendRecordsTask(context, className, databaseObjects);
                sendRecordsTask.call();
            }

            //TODO: get changes, upload
            FetchRecordsTask frtEx = new FetchRecordsTask();
            frtEx.setClassName(className);
            frtEx.setContext(context);
            frtEx.call();
        }
        Globals.getInstance().setLatestUpdate(System.currentTimeMillis());

        //Set globals
        Globals globals = Globals.getInstance();
        if(globals.isLoggedAsPatient()==1 && globals.getPatientOb() == null) {
            globals.setPatientOb(AppDatabase.getDatabase(context).patientDao().loadByUserId(user.getId(), false));
        } else if (globals.isLoggedAsPatient()==0 && globals.getTherapistOb() == null) {
            globals.setTherapistOb(AppDatabase.getDatabase(context).therapistDao().loadByUserId(user.getId(), false));
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private User findUser(String username) {
        return AppDatabase.getDatabase(context).userDao().findByUsername(username, false);
    }
}
