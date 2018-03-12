package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.UUID;

/**
 * Created by jimi on 27/12/2017.
 */

@Entity(indices = {@Index(value = "idcomment", unique = true)})
public class Comment implements DatabaseObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idcomment")
    private String cid;

    private String patientId;

    private String exerciseId;

    private String text;

    private int sentByPatient;

    private java.sql.Date date;

    private Time time;

    private long timestamp;

    @ColumnInfo
    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Comment(String cid, java.sql.Date date, Time time, String text, String patientId, String exerciseId, int sentByPatient) {
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

    @Ignore
    public Comment(java.sql.Date date, Time time, String text, String patientId, String exerciseId, int sentByPatient) {
        cid = UUID.randomUUID().toString();
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
    public String getCid() {
            return cid;
        }
    public void setCid(String cid) {
            this.cid = cid;
    }
    public String getPatientId() {
        return patientId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public String getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(String exerciseId) {
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
