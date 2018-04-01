package com.example.jimi.mystroke.tasks;

import android.content.Context;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;

public class LoadTherapistObject implements Runnable {
    private Globals globals;
    private AppDatabase appDatabase;
    private String uId;

    public LoadTherapistObject(Context context, String uId) {
        this.globals = Globals.getInstance();
        this.appDatabase = AppDatabase.getDatabase(context);
        this.uId = uId;
    }

    @Override
    public void run() {
        globals.setTherapistOb(appDatabase.therapistDao().loadByUserId(uId, false));
    }
}
