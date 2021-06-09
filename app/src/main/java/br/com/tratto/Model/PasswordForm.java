package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/20/17.
 */

public class PasswordForm {
    @SerializedName("password")
    public String password;
    @SerializedName("password_confirmation")
    public String passwordConfirmation;
}
