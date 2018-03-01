package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by jimi on 27/12/2017.
 */

@Entity
public class Comment implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    @ColumnInfo(name = "idcomment")
    private int cid;

    private int patientId;

    private int exerciseId;

    private String text;

    private int sentByPatient;

    private java.sql.Date date;

    private Time time;

    private long timestamp;

    @ColumnInfo
    private long created;


    public Comment(int cid, java.sql.Date date, Time time, String text, int patientId, int exerciseId, int sentByPatient) {
        this.cid = cid;
        this.time = time;
        this.date = date;
        this.patientId = patientId;
        this.exerciseId = exerciseId;
        this.text = text;
        created = System.currentTimeMillis();
        timestamp = date.getTime() + time.getTime();
        this.sentByPatient = sentByPatient;
    }

    public long getCreated() {
            return created;
        }
    public void setCreated(long created) {
            this.created = created;
        }
    public int getCid() {
            return cid;
        }
    public void setCid(int cid) {
            this.cid = cid;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public java.sql.Date getDate() {
        return date;
    }
    public void setDate(java.sql.Date date) {
        this.date = date;
    }
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public int getSentByPatient() {
        return sentByPatient;
    }
    public void setSentByPatient(int sentByPatient) {
        this.sentByPatient = sentByPatient;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idcomment", cid);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        jsonObject.put("text", text);
        jsonObject.put("patient_idpatient", patientId);
        jsonObject.put("exercise_idexercise", exerciseId);
        jsonObject.put("sent_by_patient", sentByPatient);
        return jsonObject;
    }
}
