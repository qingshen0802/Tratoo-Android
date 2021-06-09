package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by lily on 9/20/17.
 */

public class NewUser {
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
    @SerializedName("under16")
    public boolean under16;
    @SerializedName("password")
    public String password;
    @SerializedName("password_confirmation")
    public String passwordConfirmation;
    @SerializedName("guarantor_email")
    public String guarantorEmail;
    @SerializedName("guarantor_full_name")
    public String guarantorFullName;
    @SerializedName("guarantor_cpf")
    public String guarantorCPF;
}
