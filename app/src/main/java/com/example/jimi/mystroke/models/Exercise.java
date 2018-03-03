package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jimi on 13/12/2017.
 */
@Entity(foreignKeys = {
                @ForeignKey(
                        entity = Assessment.class,
                        parentColumns = "idassessment",
                        childColumns = "assessment_idassessment",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "idexercise",
                        unique = true
                )
        }
)
public class Exercise implements DatabaseObject {
    //TODO: add video
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    @ColumnInfo(name = "idexercise")
    private int eid;

    @ColumnInfo(name = "verboseDescription")
    private String description;

    @ColumnInfo(name = "section")
    private String section;

    @ColumnInfo(name = "assessment_idassessment")
    private int aid;

    @ColumnInfo(name = "name")
    private String name;

    //Used to determine which objects have been changed since the last sync of the application
    @ColumnInfo
    private long created;

    @ColumnInfo
    private boolean viewed;

    public Exercise(int eid, String description, String section, String name, int aid) {
        this.eid = eid;
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        created  = new Date().getTime();
        viewed = false;
    }

    @Ignore
    public Exercise(String description, String section, String name, int aid) {
        eid = 0;
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        created  = new Date().getTime();
        viewed = false;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public int getEid() {
        return eid;
    }
    public void setEid(int eid) {
        this.eid = eid;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public int getAid() {
        return aid;
    }
    public void setAid(int aid) {
        this.aid = aid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isViewed() {
        return viewed;
    }
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idexercise", eid);
        jsonObject.put("verbose_description", description);
        jsonObject.put("section", section);
        jsonObject.put("name", name);
        jsonObject.put("assessment_idassessment", aid);
        return jsonObject;
    }
}
