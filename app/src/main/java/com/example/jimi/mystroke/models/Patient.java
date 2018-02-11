package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "patient",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "iduser",
                        childColumns = "user_iduser",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Therapist.class,
                        parentColumns = "idtherapist",
                        childColumns = "therapist_idtherapist",
                        onDelete = ForeignKey.NO_ACTION
                )
        }
)

public class Patient implements DatabaseObject {
    @PrimaryKey
    @ColumnInfo(name = "idpatient")
    private int pid;

    @ColumnInfo(name = "user_iduser")
    private int userID;

    @ColumnInfo(name = "therapist_idtherapist")
    private int therapist;

    private int active;

    @ColumnInfo
    private long created;

    public Patient(int pid, int userID, int therapist, int active) {
        this.pid = pid;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTherapist() {
        return therapist;
    }

    public void setTherapist(int therapist) {
        this.therapist = therapist;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idpatient", pid);
        jsonObject.put("user_iduser", userID);
        jsonObject.put("therapist_idtherapist", therapist);
        jsonObject.put("active", active);
        return jsonObject;
    }

}
