package com.example.jimi.mystroke.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 10/01/2018.
 */

public interface DatabaseObject {
    JSONObject toJSON() throws JSONException;
}
