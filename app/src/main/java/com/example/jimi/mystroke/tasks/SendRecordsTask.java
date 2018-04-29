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

public class SendRecordsTask implements Callable {
    private Context context;
    private String className;
    private List<? extends DatabaseObject> records;
    private String[] methods = {"PUT", "POST"};

    public SendRecordsTask(Context context, String className, List<? extends DatabaseObject> records) {
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

    //http://aboutyusata.blogspot.fi/2014/07/set-header-and-send-json-object-in.html    https://stackoverflow.com/questions/37874905/how-to-post-params-in-the-body-of-http-post-request
    @Override
    public String call() {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        HttpURLConnection con = null;
        String fullText = "";
        for (String method : methods) {
            try {
                String jsonString;
                String urlString = Globals.getInstance().getDbUrl().concat(className);
                if(method.equals("PUT")) {
                    urlString = urlString.concat("/").concat(records.get(0).getId());
                    jsonString =  records.get(0).toJSON().toString();
                } else {
                    jsonString = records.get(0).toJSONWithId().toString();
                }
                /*if(records.size() > 1) {  Multiple objects at a time
                    for (int i = 1; i < records.size(); i++) {
                        urlString = urlString.concat(",");
                        urlString = urlString.concat(records.get(i).getId());
                        //String urlStringT = urlString.concat(",").concat(records.get(i).getId());
                    }
                }*/

                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();

                //Two lines below directly from https://stackoverflow.com/questions/5379247/filenotfoundexception-while-getting-the-inputstream-object-from-httpurlconnectio/23857860
                //con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                //con.setRequestProperty("Accept","*/*");
                //con.setRequestProperty("Content-Type", "application/json");
                con.setRequestMethod(method);
                con.setDoOutput(true);

                DataOutputStream outputStreamWriter = new DataOutputStream(con.getOutputStream());
                outputStreamWriter.writeBytes(jsonString);
                outputStreamWriter.flush();
                outputStreamWriter.close();

                //con.connect();
                // Get server input
                //InputStream er = con.getErrorStream();
                //int code = con.getResponseCode();
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
        }
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
