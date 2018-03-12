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
        },
        indices = {
                @Index(
                        value = "listImageryID",
                        unique = true
                )
        }
)
public class PatientListImagery implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String listImageryID;

    private String pID;

    private String iID;

    private long created;

    private boolean toDelete;

    @Ignore
    private Imagery imagery;


    public PatientListImagery(String listImageryID, String pID, String iID) {
        this.listImageryID = listImageryID;
        this.pID = pID;
        this.iID = iID;
        created = System.currentTimeMillis();
    }

    @Ignore
    public PatientListImagery(String pID, String iID) {
        listImageryID = UUID.randomUUID().toString();
        this.pID = pID;
        this.iID = iID;
        created = System.currentTimeMillis();
    }

    public String getListImageryID() {
        return listImageryID;
    }
    public void setListImageryID(String listImageryID) {
        this.listImageryID = listImageryID;
    }
    public String getPID() {
        return pID;
    }
    public void setPID(String pID) {
        this.pID = pID;
    }
    public String getIID() {
        return iID;
    }
    public void setIID(String iID) {
        this.iID = iID;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public Imagery getImagery() {
        return imagery;
    }
    public void setImagery(Imagery imagery) {
        this.imagery = imagery;
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
        //jsonObject.put("idpatient_list_imagery", listImageryID);
        jsonObject.put("idpatient", pID);
        jsonObject.put("idimagery", iID);
        return jsonObject;
    }

    @Override
    public String toString() {
        if(imagery != null) {
            return imagery.toString();
        } else {
            //TODO don't.
            return "shit";
        }
    }
}
