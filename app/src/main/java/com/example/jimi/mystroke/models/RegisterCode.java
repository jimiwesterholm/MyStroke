package com.example.jimi.mystroke.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = "id", unique = true)})
public class RegisterCode {
    @PrimaryKey
    @NonNull
    private String id;
    //0 is patient, 1 is therapist, 2 is admin
    private int access;
    private long expires;
    private long created;
    private boolean toDelete;
    private String creatorId;

    public RegisterCode(String id, int access, String creatorId) {
        this.id = id;
        this.access = access;
        this.creatorId = creatorId;
        created = System.currentTimeMillis();
        this.expires = created + 1000 * 60 * 60 * 24;
        toDelete = false;
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

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
