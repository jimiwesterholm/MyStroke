package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import com.example.jimi.mystroke.tasks.AsyncResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

@Entity(tableName = "patient",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_iduser",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Therapist.class,
                        parentColumns = "id",
                        childColumns = "therapist_idtherapist",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "id",
                        unique = true
                ),
                @Index(
                        value = "user_iduser"
                ),
                @Index(
                        value = "therapist_idtherapist"
                )
        }
)

public class Patient implements DatabaseObject, AsyncResponse {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "user_iduser")
    private String userID;

    @ColumnInfo(name = "therapist_idtherapist")
    private String therapist;

    private int active;

    @ColumnInfo
    private long created;

    @Ignore
    private User user;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public Patient(String id, String userID, String therapist, int active) {
        this.id = id;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public Patient(String userID, String therapist, int active) {
        id = UUID.randomUUID().toString();
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public Patient(String id, String userID, String therapist, int active, User user) {
        this.id = id;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        this.user = user;
        toDelete = false;
    }

    @Ignore
    public Patient(String userID, String therapist, int active, User user) {
        id = UUID.randomUUID().toString();
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        this.user = user;
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
    public String getTherapist() {
        return therapist;
    }
    public void setTherapist(String therapist) {
        this.therapist = therapist;
    }
    public void setActive(int active) {
        this.active = active;
    }
    public int getActive() {
        return active;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        if (user != null) return getUser().getFirstName() + " " + getUser().getLastName();
        return "Error loading name";
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idpatient", id);
        jsonObject.put("user_iduser", userID);
        jsonObject.put("therapist_idtherapist", therapist);
        jsonObject.put("active", active);
        return jsonObject;
    }

    @Override
    public void respond(int var, Object... objects) {
        setUser((User) objects[0]);
    }
}
