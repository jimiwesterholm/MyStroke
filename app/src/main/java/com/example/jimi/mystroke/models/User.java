package com.example.jimi.mystroke.models; /**
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Entity(indices = {@Index(value = "iduser", unique = true)})
        public class User implements DatabaseObject {
    @PrimaryKey(autoGenerate = true)    //SQLite primary key - different from one matching the MySQL database
    private int SQLiteId;

    @ColumnInfo(name = "iduser")
    private int uid;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "salt")
    private String salt;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "patient")
    private int patient;

    @ColumnInfo(name = "therapist")
    private int therapist;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo
    private long created;

    private boolean toDelete;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public User(int uid, String username, String password, String salt, int therapist, int patient, String email, String firstName, String lastName) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.therapist = therapist;
        this.patient = patient;
        this.firstName = firstName;
        this.lastName = lastName;
        created = new Date().getTime();
    }

    @Ignore
    public User(String username, String password, String salt, int therapist, int patient, String email, String firstName, String lastName) {
        uid = 0;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.therapist = therapist;
        this.patient = patient;
        this.firstName = firstName;
        this.lastName = lastName;
        created = new Date().getTime();
    }

    //Getters and Setters
    public int getSQLiteId() {
        return SQLiteId;
    }
    public void setSQLiteId(int SQLiteId) {
        this.SQLiteId = SQLiteId;
    }
    public int getUid() {
        return uid;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getPatient() {
        return patient;
    }
    public void setPatient(int patient) {
        this.patient = patient;
    }
    public int getTherapist() {
        return therapist;
    }
    public void setTherapist(int therapist) {
        this.therapist = therapist;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("iduser", uid);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("salt", salt);
        jsonObject.put("patient", patient);
        jsonObject.put("therapist", therapist);
        jsonObject.put("email", email);
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        return jsonObject;
    }
}
