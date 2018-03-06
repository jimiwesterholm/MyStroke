package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "therapist",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "iduser",
                        childColumns = "user_iduser",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(
                        value = "idtherapist",
                        unique = true
                )
        }
)
public class Therapist implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int id;

    @ColumnInfo(name = "idtherapist")
    private int tid;

    @ColumnInfo(name = "user_iduser")
    private int userID;

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

    public Therapist(int tid, int userID, String position, int active) {
        this.tid = tid;
        this.userID = userID;
        this.position = position;
        this.active = active;
        created = System.currentTimeMillis();
    }

    @Ignore
    public Therapist(int userID, String position, int active) {
        tid = 0;
        this.userID = userID;
        this.position = position;
        this.active = active;
        created = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public int getTid() {
        return tid;
    }
    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
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
        //jsonObject.put("idtherapist", tid);
        jsonObject.put("position", position);
        jsonObject.put("user_iduser", userID);
        jsonObject.put("active", active);
        return jsonObject;
    }
}
