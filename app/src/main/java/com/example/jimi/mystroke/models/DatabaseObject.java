package com.example.jimi.mystroke.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 10/01/2018.
 */

public interface DatabaseObject {
    String getId();
    void setId(String id);
    JSONObject toJSON() throws JSONException;
}
