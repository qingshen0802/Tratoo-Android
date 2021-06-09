package br.com.tratto.Model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lily on 8/23/17.
 */

public class User {

    @SerializedName("id")
    public int id;
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("email")
    public String email;
    @SerializedName("phone_number")
    public String phoneNumber;
    @SerializedName("device_number")
    public String deviceNumber;
    @SerializedName("full_name")
    public String fullName;
    @SerializedName("document_id_number")
    public String documentIdNumber;
    @SerializedName("username")
    public String userName;
    @SerializedName("sign_in_count")
    public int signIncount;
    @SerializedName("referral_url")
    public String referralUrl;
    @SerializedName("blocked")
    public boolean blocked;
    @SerializedName("under16")
    public boolean under16;
    @SerializedName("red_alert_flag")
    public boolean redAlertFlag;
    @SerializedName("phone_confirmed?")
    public boolean phoneConfirmed;
    @SerializedName("profile_ids")
    public ArrayList<Integer> profileIds = new ArrayList<>();
    @SerializedName("referral_link")
    public String referralLink;
    @SerializedName("password")
    public String password;
    @SerializedName("password_confirmation")
    public String passwordConfirmation;
}
