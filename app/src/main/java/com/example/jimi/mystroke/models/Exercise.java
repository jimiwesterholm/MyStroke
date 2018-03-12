package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

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
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idexercise")
    private String eid;

    @ColumnInfo(name = "verboseDescription")
    private String description;

    @ColumnInfo(name = "section")
    private String section;

    @ColumnInfo(name = "assessment_idassessment")
    private String aid;

    @ColumnInfo(name = "name")
    private String name;

    //Used to determine which objects have been changed since the last sync of the application
    @ColumnInfo
    private long created;

    @ColumnInfo
    private boolean viewed;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Exercise(String eid, String description, String section, String name, String aid) {
        this.eid = eid;
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        created  = new Date().getTime();
        viewed = false;
    }

    @Ignore
    public Exercise(String description, String section, String name, String aid) {
        eid = UUID.randomUUID().toString();
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        created  = new Date().getTime();
        viewed = false;
    }

    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
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
    public String getAid() {
        return aid;
    }
    public void setAid(String aid) {
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
