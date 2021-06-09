package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.User;

/**
 * Created by lily on 9/5/17.
 */

public class UserResponse {

    @SerializedName("user")
    public User user;
    @SerializedName("user_id")
    public String userId;
}