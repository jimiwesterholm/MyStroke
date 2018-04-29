package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.util.Log;

import com.example.jimi.mystroke.models.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.JSONtoSQLite;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

import static android.content.ContentValues.TAG;

/**
 * Created by jimi on 27/11/2017.
 */

public class DeleteRecordsTask implements Callable {
    private Context context;
    private String className;
    private List<? extends DatabaseObject> records;

    public DeleteRecordsTask(Context context, String className, List<? extends DatabaseObject> records) {
        this.context = context;
        this.className = className;
        this.records = records;
    }

    public List<? extends DatabaseObject> getRecords() {
        return records;
    }
    public void setRecords(List<? extends DatabaseObject> records) {
        this.records = records;
    }
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
        HttpURLConnection con = null;
        String fullText = "";
            try {
                String urlString = Globals.getInstance().getDbUrl().concat(className).concat("/");
                urlString = urlString.concat(records.get(0).getId());
                if(records.size() > 1) {
                    for (int i = 1; i < records.size(); i++) {
                        urlString = urlString.concat(",");
                        urlString = urlString.concat(records.get(i).getId());
                        //String urlStringT = urlString.concat(",").concat(records.get(i).getId());
                    }
                }

                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("DELETE");

                InputStream is = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = br.readLine();
                Log.i(TAG, line);
                while (line != null) {
                    fullText += line + "\n";
                    line = br.readLine();
                }
                br.close();
            } catch (Exception exception) {
                Log.e(TAG, exception.getMessage());
            } finally {
                //Log.i(TAG, fullText);
                if (con != null) {
                    con.disconnect();
                }
            }
            fullText = "";
        return null;
    }

    public String recordsToString(List<? extends DatabaseObject> records) throws JSONException {
        String result = "";
        for (DatabaseObject record : records) {
            result.concat(record.toJSON().toString());
        }
        return result;
    }
}
