package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by jimi on 30/12/2017.
 */
@Entity(indices = {@Index(value = "imageryID", unique = true)})
public class Imagery implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String imageryID;

    private String name;

    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Imagery(String imageryID, String name) {
        this.imageryID = imageryID;
        this.name = name;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Imagery(String name) {
        imageryID = UUID.randomUUID().toString();
        this.name = name;
        created = System.currentTimeMillis();
    }

    public String getImageryID() {
        return imageryID;
    }
    public void setImageryID(String imageryID) {
        this.imageryID = imageryID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        //jsonObject.put("idimagery", imageryID);
        jsonObject.put("name", name);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getName();
    }
}
