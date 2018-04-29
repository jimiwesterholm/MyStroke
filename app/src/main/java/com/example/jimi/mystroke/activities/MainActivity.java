package com.example.jimi.mystroke.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.tasks.FetchRecordsTask;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.tasks.SyncDatabaseTask;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = AppDatabase.getDatabase(getApplicationContext());
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        //Get login details TODO: add offline support
        FetchRecordsTask frtUser = new FetchRecordsTask();
        frtUser.setClassName("user");
        frtUser.setContext(context);
        Future<String> future = Executors.newSingleThreadExecutor().submit(frtUser);
        String temp = null;

        FetchRecordsTask frtRC = new FetchRecordsTask();
        frtRC.setClassName("register_code");
        frtRC.setContext(context);
        Executors.newSingleThreadExecutor().submit(frtRC);

        TextView text = findViewById(R.id.text);
        try {
            text.setText(getString(R.string.loading));
            long timeout = System.currentTimeMillis() + 10000;
            do {
                temp = future.get();
            } while (!future.isDone() || System.currentTimeMillis() >= timeout);
            Globals.getInstance().setLatestUpdate(System.currentTimeMillis());
            //Go to log in once details recovered
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
