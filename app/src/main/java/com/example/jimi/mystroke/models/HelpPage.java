package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by jimi on 24/03/2018.
 */
@Entity(tableName = "help_page",
        indices = {@Index(value = "id", unique = true)})
public class HelpPage implements DatabaseObject {
    public static final int classNameIndex = 3;

    @PrimaryKey
    @NonNull
    private String id;
    private String body;
    private String title;
    private String parentId;
    private long created;
    private boolean toDelete;

    public HelpPage(@NonNull String id, String body, String title, String parentId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.parentId = parentId;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public HelpPage(String body, String title, String parentId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.body = body;
        this.parentId = parentId;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public boolean isToDelete() {
        return toDelete;
    }
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idhelp_link_page", id);
        jsonObject.put("body", body);
        jsonObject.put("title", title);
        jsonObject.put("id_parent", parentId);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
