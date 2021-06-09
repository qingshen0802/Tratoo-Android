package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/6/17.
 */

public class ResponseModel {

    @SerializedName("page")
    public int page;
    @SerializedName("per_page")
    public int perPage;
    @SerializedName("count")
    public int count;
}
