package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/6/17.
 */

public class ErrorResponse {

    @SerializedName("error")
    public boolean error;

    @SerializedName("message")
    public String message;
}
