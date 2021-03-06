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

@Entity(tableName = "assessment_result_string",
        indices = {@Index(value = "id", unique = true)})
public class AssessmentResultString implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String assessmentItemId;
    private String therapistAssessmentId;
    private String pId;
    private String value;
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
    public String getPId() {
        return pId;
    }
    public void setPId(String pId) {
        this.pId = pId;
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
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public AssessmentResultString(String id, String assessmentItemId, String therapistAssessmentId, String value) {
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
        //jsonObject.put("id_assessment_result_string", id);
        jsonObject.put("idtherapist_assesses_exercise", therapistAssessmentId);
        jsonObject.put("value", value);
        return jsonObject;
    }

    @Override
    public JSONObject toJSONWithId() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_assessment_result_string", id);
        jsonObject.put("idtherapist_assesses_exercise", therapistAssessmentId);
        jsonObject.put("value", value);
        return jsonObject;
    }
}
