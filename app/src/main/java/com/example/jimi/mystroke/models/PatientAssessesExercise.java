package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

/**
 * Created by jimi on 28/12/2017.
 */
//TODO PATIENTS DON'T DO THIS, THERAPISTS DO
@Entity(tableName = "patient_assesses_exercise",
        foreignKeys = {
                @ForeignKey(
                        entity = Patient.class,
                        parentColumns = "id",
                        childColumns = "pID",
                        onDelete = ForeignKey.NO_ACTION
                ),
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "eID",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "id",
                        unique = true
                ),
                @Index(
                        value = "pID"
                ),
                @Index(
                        value = "eID"
                )
        }
)
public class PatientAssessesExercise implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String pID;
    private String eID;
    private int score;
    private java.sql.Date date;
    private Time time;
    private long timestamp;
    private long created;
    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public PatientAssessesExercise(@NonNull  String id, String pID, String eID, int score, java.sql.Date date, Time time) {
        this.id = id;
        this.pID = pID;
        this.eID = eID;
        this.score = score;
        this.time = time;
        this.date = date;
        timestamp = date.getTime() + time.getTime();
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public PatientAssessesExercise(String pID, String eID, int score) {
        id = UUID.randomUUID().toString();
        this.pID = pID;
        this.eID = eID;
        this.score = score;
        created = System.currentTimeMillis();
        timestamp = created;
        this.date = new Date(timestamp);
        this.time = new Time(timestamp - date.getTime());
        toDelete = false;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPID() {
        return pID;
    }
    public void setPID(String pID) {
        this.pID = pID;
    }
    public String getEID() {
        return eID;
    }
    public void setEID(String eID) {
        this.eID = eID;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idpatient_assesses_exercise", id);
        jsonObject.put("idpatient_idpatient", pID);
        jsonObject.put("exercise_idexercise", eID);
        jsonObject.put("score", score);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        return jsonObject;
    }

    @Override
    public JSONObject toJSONWithId() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idpatient_assesses_exercise", id);
        jsonObject.put("idpatient_idpatient", pID);
        jsonObject.put("exercise_idexercise", eID);
        jsonObject.put("score", score);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        return jsonObject;
    }
}
