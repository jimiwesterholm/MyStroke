package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by jimi on 13/12/2017.
 */

@Entity(indices = {@Index(value = "id", unique = true)})
public class Assessment implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String label;
    private long created;
    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Assessment(@NonNull String id, String label) {
        this.id = id;
        this.label = label;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public Assessment(String label) {
        id = UUID.randomUUID().toString();
        this.label = label;
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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idassessment", id);
        jsonObject.put("name", label);
        return jsonObject;
    }

    @Override
    public JSONObject toJSONWithId() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idassessment", id);
        jsonObject.put("name", label);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}