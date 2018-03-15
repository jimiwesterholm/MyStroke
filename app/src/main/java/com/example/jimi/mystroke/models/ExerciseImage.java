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
@Entity(tableName = "exercise_image",
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "idexercise",
                        childColumns = "eid",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "eid"
                )
        }
)
public class ExerciseImage implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String exerciseImageId;

    private String eid;

    private String altText;

    private int position;

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


    public ExerciseImage(@NonNull String exerciseImageId, String eid, String altText, int position) {
        this.exerciseImageId = exerciseImageId;
        this.eid = eid;
        this.altText = altText;
        this.position = position;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public ExerciseImage(String eid, String altText, int position) {
        this.exerciseImageId = UUID.randomUUID().toString();
        this.eid = eid;
        this.altText = altText;
        this.position = position;
        created = System.currentTimeMillis();
        toDelete = false;
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
    @NonNull
    public String getExerciseImageId() {
        return exerciseImageId;
    }
    public void setExerciseImageId(@NonNull String exerciseImageId) {
        this.exerciseImageId = exerciseImageId;
    }
    public String getAltText() {
        return altText;
    }
    public void setAltText(String altText) {
        this.altText = altText;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return getAltText();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idexercise", eid);
        jsonObject.put("id_exercise_image", exerciseImageId);
        jsonObject.put("exercise_idexercise", eid);
        jsonObject.put("position", position);
        jsonObject.put("alt_text", altText);
        return jsonObject;
    }
}
