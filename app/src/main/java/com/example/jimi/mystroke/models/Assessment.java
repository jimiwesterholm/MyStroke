package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.jimi.mystroke.DatabaseObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jimi on 13/12/2017.
 */

@Entity
public class Assessment implements DatabaseObject {
    @PrimaryKey
    @ColumnInfo(name = "idassessment")
    private int aid;

    private double scoreMin;

    private double scoreMax;

    private String label;

    @ColumnInfo
    private long created;

    public Assessment(int aid, double scoreMin, double scoreMax, String label) {
        this.aid = aid;
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
    public int getAid() {
        return aid;
    }
    public void setAid(int aid) {
        this.aid = aid;
    }
    public double getScoreMin() {
        return scoreMin;
    }
    public void setScoreMin(double scoreMin) {
        this.scoreMin = scoreMin;
    }
    public double getScoreMax() {
        return scoreMax;
    }
    public void setScoreMax(double scoreMax) {
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
        jsonObject.put("idassessment", aid);
        jsonObject.put("scoreMin", scoreMin);
        jsonObject.put("scoreMax", scoreMax);
        jsonObject.put("label", label);
        return jsonObject;
    }
}