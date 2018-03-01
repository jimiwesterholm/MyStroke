package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

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
        }
)
public class PatientListExercise implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    private int listExerciseID;

    private int pID;

    private int eID;

    private String message;

    private long created;

    public PatientListExercise(int listExerciseID, int pID, int eID, String message) {
        this.listExerciseID = listExerciseID;
        this.pID = pID;
        this.eID = eID;
        this.message = message;
        created = System.currentTimeMillis();
    }

    public int getListExerciseID() {
        return listExerciseID;
    }
    public void setListExerciseID(int listExerciseID) {
        this.listExerciseID = listExerciseID;
    }
    public int getPID() {
        return pID;
    }
    public void setPID(int pID) {
        this.pID = pID;
    }
    public int getEID() {
        return eID;
    }
    public void setEID(int eID) {
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

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("patient_list_exerciseid", listExerciseID);
        jsonObject.put("patient_id", pID);
        jsonObject.put("exercise_id", eID);
        return jsonObject;
    }
}
