package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 30/12/2017.
 */
@Entity(indices = {@Index(value = "imageryID", unique = true)})
public class Imagery implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    private int imageryID;

    private String name;

    private long created;

    public Imagery(int imageryID, String name) {
        this.imageryID = imageryID;
        this.name = name;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Imagery(String name) {
        imageryID = 0;
        this.name = name;
        created = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
        //jsonObject.put("idimagery", imageryID);
        jsonObject.put("name", name);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getName();
    }
}
