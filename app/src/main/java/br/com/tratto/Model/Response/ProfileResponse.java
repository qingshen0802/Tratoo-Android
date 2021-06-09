package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Profile;

/**
 * Created by lily on 9/6/17.
 */

public class ProfileResponse extends ResponseModel{

    @SerializedName("profiles")
    public ArrayList<Profile> profiles;
}
