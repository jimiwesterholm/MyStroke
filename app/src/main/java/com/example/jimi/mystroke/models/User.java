package com.example.jimi.mystroke.models;
/*
 * Created by jimi on 25/11/2017.
 */
import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

@Entity(indices = {@Index(value = "id", unique = true)})
public class User implements DatabaseObject {
    public static final int classNameIndex = 0;

    @PrimaryKey
    @NonNull
    private String id;

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

    @Ignore
    private Patient patientOb;

    @Ignore
    private Therapist therapistOb;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    public User(String id, String username, String password, String salt, int patient, int therapist, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.patient = patient;
        this.therapist = therapist;
        this.firstName = firstName;
        this.lastName = lastName;
        created = new Date().getTime();
        toDelete = false;
    }

    @Ignore
    public User(String username, String password, String salt, int patient, int therapist, String email, String firstName, String lastName) {
        id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.patient = patient;
        this.therapist = therapist;
        this.firstName = firstName;
        this.lastName = lastName;
        created = new Date().getTime();
        toDelete = false;
    }

    //Getters and Setters
    public Patient getPatientOb() {
        return patientOb;
    }
    public void setPatientOb(Patient patientOb) {
        this.patientOb = patientOb;
    }
    public Therapist getTherapistOb() {
        return therapistOb;
    }
    public void setTherapistOb(Therapist therapistOb) {
        this.therapistOb = therapistOb;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
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
        //jsonObject.put("iduser", id);
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

    @Override
    public JSONObject toJSONWithId() throws JSONException { //TODO do this for everything else
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iduser", id);
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
