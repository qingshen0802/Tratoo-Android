package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.NewUser;

/**
 * Created by lily on 9/21/17.
 */

public class RequestNewUser {

    @SerializedName("user")
    public NewUser user;

    public RequestNewUser() {
    }

    public RequestNewUser(NewUser user) {
        this.user = user;
    }
}
