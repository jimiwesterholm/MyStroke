package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.GetChangedRecords;
import com.example.jimi.mystroke.GetToBeDeleted;
import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.User;

import java.util.List;

public class SyncDatabaseTask implements Runnable {
    private Context context;

    public SyncDatabaseTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        String[] mediaClassNames = Globals.getInstance().getMediaClassNames();
        String[] classNames = Globals.getInstance().getClassNames();
        User loggedInUser = Globals.getInstance().getUser();
        User user = findUser(loggedInUser.getUsername());
        if(user == null || !user.getPassword().equals(loggedInUser.getPassword()) || !user.getSalt().equals(loggedInUser.getSalt())) {
            return;
        }

        for (String className : classNames) {
            List<? extends DatabaseObject> databaseObjects = new GetChangedRecords(context).getChanged(className);
            List<? extends DatabaseObject> databaseObjectsToDelete = new GetToBeDeleted(context).getToBeDeleted(className);
            if(databaseObjects != null) {
                if(databaseObjects.size() > 0) {
                    SendRecordsTask sendRecordsTask = new SendRecordsTask(context, className, databaseObjects);
                    sendRecordsTask.call();
                }
            }
            if(databaseObjectsToDelete != null) {
                if (databaseObjectsToDelete.size() > 0) {
                    DeleteRecordsTask deleteRecordsTask = new DeleteRecordsTask(context, className, databaseObjectsToDelete);
                    deleteRecordsTask.call();
                }
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
