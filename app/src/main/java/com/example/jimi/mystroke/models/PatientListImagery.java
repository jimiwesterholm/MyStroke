package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 28/12/2017.
 */
@Entity(tableName = "patient_list_imagery",
        foreignKeys = {
                @ForeignKey(
                        entity = Patient.class,
                        parentColumns = "idpatient",
                        childColumns = "pID",
                        onDelete = ForeignKey.NO_ACTION
                ),
                @ForeignKey(
                        entity = Imagery.class,
                        parentColumns = "imageryID",
                        childColumns = "iID",
                        onDelete = ForeignKey.NO_ACTION
                )
        }
)
public class PatientListImagery implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    private int listImageryID;

    private int pID;

    private int iID;

    private long created;

    public PatientListImagery(int listImageryID, int pID, int iID) {
        this.listImageryID = listImageryID;
        this.pID = pID;
        this.iID = iID;
        created = System.currentTimeMillis();
    }

    public PatientListImagery(int pID, int iID) {
        listImageryID = 0;
        this.pID = pID;
        this.iID = iID;
        created = System.currentTimeMillis();
    }

    public int getListImageryID() {
        return listImageryID;
    }
    public void setListImageryID(int listImageryID) {
        this.listImageryID = listImageryID;
    }
    public int getPID() {
        return pID;
    }
    public void setPID(int pID) {
        this.pID = pID;
    }
    public int getIID() {
        return iID;
    }
    public void setIID(int iID) {
        this.iID = iID;
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
        //jsonObject.put("idpatient_list_imagery", listImageryID);
        jsonObject.put("idpatient", pID);
        jsonObject.put("idimagery", iID);
        return jsonObject;
    }
}
