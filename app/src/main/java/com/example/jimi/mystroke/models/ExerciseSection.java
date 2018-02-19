package com.example.jimi.mystroke.models;

/**
 * Created by jimi on 18/02/2018.
 */

public class ExerciseSection {
    private int alerts;
    private String name;

    public ExerciseSection(int alerts, String name) {
        this.alerts = alerts;
        this.name = name;
    }

    public int getAlerts() {
        return alerts;
    }
    public void setAlerts(int alerts) {
        this.alerts = alerts;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
