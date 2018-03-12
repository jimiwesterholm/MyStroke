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

@Entity(indices = {@Index(value = "idassessment", unique = true)})
public class Assessment implements DatabaseObject {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idassessment")
    private String aid;

    private int scoreMin;

    private int scoreMax;

    private String label;

    @ColumnInfo
    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Assessment(String aid, int scoreMin, int scoreMax, String label) {
        this.aid = aid;
        this.scoreMin = scoreMin;
        this.scoreMax = scoreMax;
        this.label = label;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Assessment(int scoreMin, int scoreMax, String label) {
        aid = UUID.randomUUID().toString();
        this.scoreMin = scoreMin;
        this.scoreMax = scoreMax;
        this.label = label;
        created = System.currentTimeMillis();
    }

    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
        this.aid = aid;
    }
    public int getScoreMin() {
        return scoreMin;
    }
    public void setScoreMin(int scoreMin) {
        this.scoreMin = scoreMin;
    }
    public int getScoreMax() {
        return scoreMax;
    }
    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
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
        //jsonObject.put("idassessment", aid);
        jsonObject.put("scoreMin", scoreMin);
        jsonObject.put("scoreMax", scoreMax);
        jsonObject.put("label", label);
        return jsonObject;
    }
}
