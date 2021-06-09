package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/21/17.
 */

public class NewUserResponse {

    @SerializedName("user_id")
    public String id;
    @SerializedName("under16")
    public boolean under16;
    @SerializedName("under16_confirmed")
    public boolean under16Confirmed;
}
