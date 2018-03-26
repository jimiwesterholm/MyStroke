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

@Entity(tableName = "assessment_result_double",
        indices = {@Index(value = "id", unique = true)})
public class AssessmentResultDouble implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;
    private String assessmentItemId;
    private String therapistAssessmentId;
    private double value;
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
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    public AssessmentResultDouble(String id, String assessmentItemId, String therapistAssessmentId, double value) {
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
        jsonObject.put("id_assessment_result_double", id);
        jsonObject.put("idtherapist_assesses_exercise", therapistAssessmentId);
        jsonObject.put("value", value);
        return jsonObject;
    }

}
