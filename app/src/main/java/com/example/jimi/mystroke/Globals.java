package com.example.jimi.mystroke;

import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.models.Therapist;
import com.example.jimi.mystroke.models.User;

/**
 * Created by jimi on 25/12/2017.
 */

public class Globals {
    private static final Globals ourInstance = new Globals();
    private User user;
    private Patient patientOb;
    private int isLoggedAsPatient;
    private long latestUpdate;
    private final String ngrok = "http://5767e355.ngrok.io";
    private final String dbUrl = ngrok.concat("/api.php/");
    private final String fileDispUrl = ngrok.concat("/FileDispenser/");
    private final String addImageFD = "addImage.php";
    private final String loadImageFD = "addImage.php";
    private final String[] classNames = {
            "user",
            "therapist",
            "patient",
            "help_page",
            "assessment",
            "exercise",
            "imagery",
            "patient_assesses_exercise",
            "therapist_assesses_exercise",
            "patient_list_exercise",
            "patient_list_imagery",
            "comment",
            "assessment_item",
            "assessment_result_double",
            "assessment_result_int",
            "assessment_result_string",
            "assessment_result_boolean"
    };
    private final String[] mediaClassNames = {"exercise_image"};

    //TODO remove NEXT A-TASK INT: 23

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

    public void setPatientOb(Patient patientOb) {
        this.patientOb = patientOb;
    }

    public Patient getPatientOb() {
        return patientOb;
    }

    public String getLoadImageFD() {
        return loadImageFD;
    }

    public String getFileDispUrl() {
        return fileDispUrl;
    }

    public String getAddImageFD() {
        return addImageFD;
    }

    public int isLoggedAsPatient() {
        return isLoggedAsPatient;
    }

    public void setLoggedAsPatient(int patient) {
        isLoggedAsPatient = patient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(long latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String[] getClassNames() {
        return classNames;
    }

    public String[] getMediaClassNames() {
        return mediaClassNames;
    }
}
