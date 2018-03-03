package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.*;

import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetUserTask;

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
        },
        indices = {
                @Index(
                        value = "idpatient",
                        unique = true
                )
        }
)

public class Patient implements DatabaseObject, AsyncResponse {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    @ColumnInfo(name = "idpatient")
    private int pid;

    @ColumnInfo(name = "user_iduser")
    private int userID;

    @ColumnInfo(name = "therapist_idtherapist")
    private int therapist;

    private int active;

    @ColumnInfo
    private long created;

    @Ignore
    private User user;

    public Patient(int pid, int userID, int therapist, int active) {
        this.pid = pid;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Patient(int userID, int therapist, int active) {
        pid = 0;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Patient(int pid, int userID, int therapist, int active, User user) {
        this.pid = pid;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        this.user = user;
    }

    @Ignore
    public Patient(int userID, int therapist, int active, User user) {
        pid = 0;
        this.userID = userID;
        this.therapist = therapist;
        this.active = active;
        created = System.currentTimeMillis();
        this.user = user;
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (user != null) return getUser().getFirstName() + " " + getUser().getLastName();
        return "Error loading name";
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("idpatient", pid);
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
