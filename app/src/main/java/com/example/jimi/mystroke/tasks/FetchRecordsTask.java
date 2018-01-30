package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.JSONtoSQLite;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 27/11/2017.
 */

public class FetchRecordsTask implements Callable {
    private Context context;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String call() {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        String fullText = "";
        HttpURLConnection con = null;
        try {
            URL url = new URL(Globals.getInstance().getDbUrl().concat(className));
            con = (HttpURLConnection) url.openConnection();
            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line =  br.readLine();
            Log.i(TAG, line);
            while (line != null) {
                fullText += line + "\n";
                line = br.readLine();
            }
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage());
        } finally {
            Log.i(TAG, fullText);
            if (con != null) {
                con.disconnect();
            }
        }

        try {
            if(JSONtoSQLite.parseAndUploadJSON(fullText, className, context)) {
                return fullText;
            }
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        return null;
    }
}
