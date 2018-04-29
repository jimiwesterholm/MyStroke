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
@Entity(/*foreignKeys = {   Not implemented
                @ForeignKey(
                        entity = Assessment.class,
                        parentColumns = "id",
                        childColumns = "assessment_idassessment",
                        onDelete = ForeignKey.NO_ACTION
                )
        },*/
        indices = {
                @Index(
                        value = "id",
                        unique = true
                ),
                @Index(
                        value = "assessment_idassessment"
                )
        }
)
public class Exercise implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "verboseDescription")
    private String description;

    @ColumnInfo(name = "section")
    private String section;

    @ColumnInfo(name = "assessment_idassessment")
    private String aid;

    @ColumnInfo(name = "name")
    private String name;

    private String video;

    //Used to determine which objects have been changed since the last sync of the application
    @ColumnInfo
    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Exercise(String id, String description, String section, String name, String aid, String video) {
        this.id = id;
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        created  = new Date().getTime();
        this.video = video;
        toDelete = false;
    }

    @Ignore
    public Exercise(String description, String section, String name, String aid, String video) {
        id = UUID.randomUUID().toString();
        this.description = description;
        this.section = section;
        this.name = name;
        this.aid = aid;
        this.video = video;
        created  = new Date().getTime();
        toDelete = false;
    }

    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idexercise", id);
        jsonObject.put("verbose_description", description);
        jsonObject.put("section", section);
        jsonObject.put("name", name);
        jsonObject.put("assessment_idassessment", aid);
        return jsonObject;
    }

    @Override
    public JSONObject toJSONWithId() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idexercise", id);
        jsonObject.put("verbose_description", description);
        jsonObject.put("section", section);
        jsonObject.put("name", name);
        jsonObject.put("assessment_idassessment", aid);
        return jsonObject;
    }

    public static String vidIdFromYouTube(String link) {
        if(link.contains("?v=")) {
            return link.substring(link.indexOf("?v=") + 3);
        } else {
            return null;
        }
    }

    public static String youTubeFromVidId(String id) {
        return "https://www.youtube.com/watch?v=" + id;
    }

    public static String thumbnailFromVidId(String id) {
        return "http://img.youtube.com/vi/"+id+"/mqdefault.jpg";
    }
}
