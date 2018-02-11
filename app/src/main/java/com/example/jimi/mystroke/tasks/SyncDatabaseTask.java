package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.models.User;

import java.util.List;

/**
 * Created by jimi on 25/12/2017.
 */

public class SyncDatabaseTask implements Runnable {
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

    private User findUser(int id) {
        return null;
    }
}
