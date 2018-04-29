package com.example.jimi.mystroke.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.example.jimi.mystroke.Globals;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by jimi on 13/12/2017.
 */
@Entity(tableName = "exercise_image",
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "eid",
                        onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(
                        value = "eid"
                )
        }
)
public class ExerciseImage implements DatabaseObject {
    @PrimaryKey
    @NonNull
    private String id;

    private String eid;

    private String altText;

    private int position;

    //Used to determine which objects have been changed since the last sync of the application
    @ColumnInfo
    private long created;

    @Ignore
    private String address;

    private boolean toDelete;

    @Ignore
    private Bitmap bitmap;

    public boolean isToDelete() {
        return toDelete;
    }

    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }


    public ExerciseImage(@NonNull String id, String eid, String altText, int position) {
        this.id = id;
        this.eid = eid;
        this.altText = altText;
        this.position = position;
        this.address = ExerciseImage.urlFromID(id);
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public ExerciseImage(String eid, String altText, int position) {
        this.id = UUID.randomUUID().toString();
        this.eid = eid;
        this.altText = altText;
        this.position = position;
        this.address = ExerciseImage.urlFromID(id);
        created = System.currentTimeMillis();
        toDelete = false;
    }

    @Ignore
    public ExerciseImage(String eid, String altText, int position, String address) {
        this.id = UUID.randomUUID().toString();
        this.eid = eid;
        this.altText = altText;
        this.position = position;
        this.address = address;
        created = System.currentTimeMillis();
        toDelete = false;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public long getCreated() {
        return created;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    @NonNull
    public String getId() {
        return id;
    }
    public void setId(@NonNull String id) {
        this.id = id;
    }
    public String getAltText() {
        return altText;
    }
    public void setAltText(String altText) {
        this.altText = altText;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return getAltText();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("id_exercise_image", id);
        jsonObject.put("exercise_idexercise", eid);
        jsonObject.put("position", position);
        jsonObject.put("alt_text", altText);
        return jsonObject;
    }

    @Override
    public JSONObject toJSONWithId() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_exercise_image", id);
        jsonObject.put("exercise_idexercise", eid);
        jsonObject.put("position", position);
        jsonObject.put("alt_text", altText);
        return jsonObject;
    }

    public JSONObject toJSONWithBitmap() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id_exercise_image", id);
        jsonObject.put("exercise_idexercise", eid);
        jsonObject.put("position", position);
        jsonObject.put("alt_text", altText);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteImage = byteArrayOutputStream.toByteArray();
        String image = Base64.encodeToString(byteImage, Base64.DEFAULT);

        jsonObject.put("data", image);
        return jsonObject;
    }

    public static String urlFromID(String id) {
        return Globals.getInstance().getFileDispUrl().concat(id).concat(Globals.getInstance().getImageFileType());
    }
}
