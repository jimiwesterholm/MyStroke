package com.example.jimi.mystroke.tasks;

import android.content.Context;
import android.util.Log;

import com.example.jimi.mystroke.DatabaseObject;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.JSONtoSQLite;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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

public class SendRecordsTask implements Callable {
    private Context context;
    private String className;
    List<DatabaseObject> records;

    public List<DatabaseObject> getRecords() {
        return records;
    }
    public void setRecords(List<DatabaseObject> records) {
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

    //http://aboutyusata.blogspot.fi/2014/07/set-header-and-send-json-object-in.html    https://stackoverflow.com/questions/37874905/how-to-post-params-in-the-body-of-http-post-request
    @Override
    public String call() {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        String fullText = "";
        HttpURLConnection con = null;
        try {
            URL url = new URL(Globals.getInstance().getDbUrl().concat(className));
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.connect();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
            outputStreamWriter.write(recordsToString(records));
            //convert records to json, then call toString()

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

    public String recordsToString(List<DatabaseObject> records) throws JSONException {
        String result = null;
        for (DatabaseObject record : records) {
            result.concat(record.toJSON().toString());
        }
        return result;
    }
}
