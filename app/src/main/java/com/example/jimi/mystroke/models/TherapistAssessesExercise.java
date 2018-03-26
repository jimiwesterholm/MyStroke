package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.UUID;

/**
 * Created by jimi on 28/12/2017.
 */
@Entity(tableName = "therapist_assesses_exercise",
        foreignKeys = {
                @ForeignKey(
                        entity = Therapist.class,
                        parentColumns = "id",
                        childColumns = "tID",
                        onDelete = ForeignKey.NO_ACTION
                ),
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
                        value = "tID"
                ),
                @Index(
                        value = "pID"
                ),
                @Index(
                        value = "eID"
                )
        }
)
public class TherapistAssessesExercise implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String pID;
    private String eID;
    private String tID;
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

    public TherapistAssessesExercise(@NonNull String id, String pID, String eID, String tID, java.sql.Date date, Time time) {
        this.id = id;
        this.pID = pID;
        this.eID = eID;
        this.time = time;
        this.date = date;
        this.tID = tID;
        timestamp = date.getTime() + time.getTime();
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public TherapistAssessesExercise(String pID, String eID, String tID, java.sql.Date date, Time time) {
        id = UUID.randomUUID().toString();
        this.pID = pID;
        this.eID = eID;
        this.time = time;
        this.date = date;
        this.tID = tID;
        timestamp = date.getTime() + time.getTime();
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
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
    public String getTID() {
        return tID;
    }
    public void setTID(String tID) {
        this.tID = tID;
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
        jsonObject.put("idtherapist_assesses_exercise", id);
        jsonObject.put("patient_idpatient", pID);
        jsonObject.put("exercise_idexercise", eID);
        jsonObject.put("therapist_idtherapist", tID);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        return jsonObject;
    }
}
