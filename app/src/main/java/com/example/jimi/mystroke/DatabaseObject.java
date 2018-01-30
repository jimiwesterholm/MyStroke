package com.example.jimi.mystroke;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jimi on 10/01/2018.
 */

public interface DatabaseObject {
    public JSONObject toJSON() throws JSONException;
}
