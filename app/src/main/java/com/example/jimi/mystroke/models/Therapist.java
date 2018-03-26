package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

@Entity(tableName = "therapist",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_iduser",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(
                        value = "id",
                        unique = true
                ),
                @Index(
                        value = "user_iduser"
                )
        }
)
public class Therapist implements DatabaseObject {
    public static final int classNameIndex = 1;

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "user_iduser")
    private String userID;

    private String position;

    private int active;

    @ColumnInfo
    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Therapist(String id, String userID, String position, int active) {
        this.id = id;
        this.userID = userID;
        this.position = position;
        this.active = active;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public Therapist(String userID, String position, int active) {
        id = UUID.randomUUID().toString();
        this.userID = userID;
        this.position = position;
        this.active = active;
        created = System.currentTimeMillis();
        toDelete = false;
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
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public int getActive() {
        return active;
    }
    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idtherapist", id);
        jsonObject.put("position", position);
        jsonObject.put("user_iduser", userID);
        jsonObject.put("active", active);
        return jsonObject;
    }
}
