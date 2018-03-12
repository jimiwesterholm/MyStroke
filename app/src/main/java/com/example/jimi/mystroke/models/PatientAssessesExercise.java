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
//TODO PATIENTS DON'T DO THIS, THERAPISTS DO
@Entity(tableName = "patient_assessment",
        foreignKeys = {
                @ForeignKey(
                        entity = Patient.class,
                        parentColumns = "idpatient",
                        childColumns = "pID",
                        onDelete = ForeignKey.NO_ACTION
                ),
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "idexercise",
                        childColumns = "eID",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "patientAssessesExerciseID",
                        unique = true
                )
        }
)
public class PatientAssessesExercise implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String patientAssessesExerciseID;

    private String pID;

    private String eID;

    private double score;

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

    public PatientAssessesExercise(String patientAssessesExerciseID, String pID, String eID, double score, java.sql.Date date, Time time) {
        this.patientAssessesExerciseID = patientAssessesExerciseID;
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
    public PatientAssessesExercise(String pID, String eID, double score, java.sql.Date date, Time time) {
        patientAssessesExerciseID = UUID.randomUUID().toString();
        this.pID = pID;
        this.eID = eID;
        this.score = score;
        this.time = time;
        this.date = date;
        timestamp = date.getTime() + time.getTime();
        created = System.currentTimeMillis();
        toDelete = false;
    }

    public String getPatientAssessesExerciseID() {
        return patientAssessesExerciseID;
    }
    public void setPatientAssessesExerciseID(String patientAssessesExerciseID) {
        this.patientAssessesExerciseID = patientAssessesExerciseID;
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
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
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
        jsonObject.put("idpatient_assesses_exercise", patientAssessesExerciseID);
        jsonObject.put("idpatient_idpatient", pID);
        jsonObject.put("exercise_idexercise", eID);
        jsonObject.put("score", score);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        return jsonObject;
    }
}
