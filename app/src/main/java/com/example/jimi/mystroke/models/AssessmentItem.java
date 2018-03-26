package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 24/03/2018.
 */

@Entity(tableName = "assessment_item",
        indices = {@Index(value = "id", unique = true)})
public class AssessmentItem implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String assessmentId;
    private String description;
    private String dataType;
    private long created;
    private boolean toDelete;

    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public boolean isToDelete() {
        return toDelete;
    }
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }
    public String getAssessmentId() {
        return assessmentId;
    }
    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public AssessmentItem(@NonNull String id, String assessmentId, String description, String dataType) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.description = description;
        this.dataType = dataType;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_assessment_item", id);
        jsonObject.put("strategy_id", assessmentId);
        jsonObject.put("description", description);
        jsonObject.put("data_type", dataType);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
