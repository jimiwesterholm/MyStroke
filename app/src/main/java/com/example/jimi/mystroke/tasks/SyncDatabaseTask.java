package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.User;

import java.util.List;

/**
 * Created by jimi on 25/12/2017.
 */

public class SyncDatabaseTask implements Runnable {
    //TODO Make these use the string resource
    private String[] classNames = {"assessment", "exercise", "imagery", "user", "therapist", "patient", "patient_assesses_exercise", "patient_list_exercise", "patient_list_imagery", "comment",};
    private Context context;

    public SyncDatabaseTask(Context context) {
        this.context = context;
    }

    //TODO make run intermittently!!!
    @Override
    public void run() {
        User loggedInUser = Globals.getInstance().getUser();
        User user = findUser(loggedInUser.getUsername());
        if(user == null || !user.getPassword().equals(loggedInUser.getPassword()) || !user.getSalt().equals(loggedInUser.getSalt())) {
            //TODO: Consider additional security measures? - esp. server side authentication
            return;
        }

        for (String className : classNames) {
            List<? extends DatabaseObject> databaseObjects = new GetChangedRecords(context).getChanged(className);


            //TODO: get changes, upload
            FetchRecordsTask frtEx = new FetchRecordsTask();
            frtEx.setClassName(className);
            frtEx.setContext(context);
            frtEx.call();
        }
        Globals.getInstance().setLatestUpdate(System.currentTimeMillis());

        /*

         */
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private User findUser(String username) {
        return AppDatabase.getDatabase(context).userDao().findByUsername(username);
    }
}
