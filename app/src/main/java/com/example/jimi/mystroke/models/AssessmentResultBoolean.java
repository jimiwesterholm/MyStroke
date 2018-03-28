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

@Entity(tableName = "assessment_result_boolean",
        indices = {@Index(value = "id", unique = true)})
public class AssessmentResultBoolean implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String assessmentItemId;
    private String therapistAssessmentId;
    private boolean value;
    private long created;
    private boolean toDelete;

    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
        this.id = id;
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
    public String getAssessmentItemId() {
        return assessmentItemId;
    }
    public void setAssessmentItemId(String assessmentItemId) {
        this.assessmentItemId = assessmentItemId;
    }
    public String getTherapistAssessmentId() {
        return therapistAssessmentId;
    }
    public void setTherapistAssessmentId(String therapistAssessmentId) {
        this.therapistAssessmentId = therapistAssessmentId;
    }
    public boolean getValue() {
        return value;
    }
    public void setValue(boolean value) {
        this.value = value;
    }

    public AssessmentResultBoolean(String id, String assessmentItemId, String therapistAssessmentId, boolean value) {
        this.id = id;
        this.assessmentItemId = assessmentItemId;
        this.therapistAssessmentId = therapistAssessmentId;
        this.value = value;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_assessment_result_boolean", id);
        jsonObject.put("idtherapist_assesses_exercise", therapistAssessmentId);
        jsonObject.put("value", value);
        return jsonObject;
    }
}