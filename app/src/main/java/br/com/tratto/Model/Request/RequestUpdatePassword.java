package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.PasswordForm;

/**
 * Created by lily on 9/21/17.
 */

public class RequestUpdatePassword {

    @SerializedName("user")
    public PasswordForm user;

    public RequestUpdatePassword() {
    }

    public RequestUpdatePassword(PasswordForm user) {
        this.user = user;
    }
}
