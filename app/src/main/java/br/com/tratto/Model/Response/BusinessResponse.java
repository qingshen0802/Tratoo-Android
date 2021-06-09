package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Profile;

/**
 * Created by lily on 9/6/17.
 */

public class BusinessResponse extends ResponseModel{

    @SerializedName("companies")
    public ArrayList<Profile> business;
}
