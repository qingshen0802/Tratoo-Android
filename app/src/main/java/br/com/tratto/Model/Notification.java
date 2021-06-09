package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lily on 8/30/17.
 */

public class Notification {

    @SerializedName("id")
    public int id;
    @SerializedName("type")
    public String type;
    @SerializedName("notifiable_id")
    public int notifiableId;
    @SerializedName("notifiable_type")
    public String notifiableType;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("profile_id")
    public int profileId;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("read")
    public boolean read;
    @SerializedName("importance")
    public int importance;
    @SerializedName("color")
    public String color;
    @SerializedName("from_user_name")
    public String fromUserName;
    @SerializedName("from_user_photo_url")
    public String fromUserPhotoUrl;
    @SerializedName("to_user_name")
    public String toUserName;
    @SerializedName("to_user_photo_url")
    public String toUserPhotoUrl;
}
