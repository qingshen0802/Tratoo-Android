package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Profile;

/**
 * Created by lily on 9/26/17.
 */

public class PeopleResponse extends ResponseModel {

    @SerializedName("people")
    public ArrayList<Profile> profiles;
}
