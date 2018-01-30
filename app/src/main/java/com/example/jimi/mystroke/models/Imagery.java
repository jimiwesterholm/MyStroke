package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.jimi.mystroke.DatabaseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 30/12/2017.
 */
@Entity
public class Imagery implements DatabaseObject {
    @PrimaryKey
    private int imageryID;

    private String name;

    private long created;

    public Imagery(int imageryID, String name) {
        this.imageryID = imageryID;
        this.name = name;
        created = System.currentTimeMillis();
    }

    public int getImageryID() {
        return imageryID;
    }
    public void setImageryID(int imageryID) {
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
        jsonObject.put("idimagery", imageryID);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
