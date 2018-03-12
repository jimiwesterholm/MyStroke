package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by jimi on 28/12/2017.
 */
@Entity(tableName = "patient_list_exercise",
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
                        value = "listExerciseID",
                        unique = true
                )
        }
)
public class PatientListExercise implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String listExerciseID;

    private String pID;

    private String eID;

    private String message;

    private long created;

    private boolean toDelete;

    @Ignore
    private Exercise exercise;


    public PatientListExercise(String listExerciseID, String pID, String eID, String message) {
        this.listExerciseID = listExerciseID;
        this.pID = pID;
        this.eID = eID;
        this.message = message;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public PatientListExercise(String pID, String eID, String message) {
        listExerciseID = UUID.randomUUID().toString();
        this.pID = pID;
        this.eID = eID;
        this.message = message;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    public String getListExerciseID() {
        return listExerciseID;
    }
    public void setListExerciseID(String listExerciseID) {
        this.listExerciseID = listExerciseID;
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
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Exercise getExercise() {
        return exercise;
    }
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public boolean isToDelete() {
        return toDelete;
    }
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("patient_list_exerciseid", listExerciseID);
        jsonObject.put("patient_id", pID);
        jsonObject.put("exercise_id", eID);
        jsonObject.put("message", message);
        return jsonObject;
    }

    @Override
    public String toString() {
        if(exercise != null) {
            return exercise.toString();
        } else {
            //TODO why tho
            return message;
        }
    }
}
