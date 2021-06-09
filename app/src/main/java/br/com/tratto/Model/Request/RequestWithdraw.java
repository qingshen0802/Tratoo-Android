package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.Response.NewWithdraw;

/**
 * Created by lily on 10/7/17.
 */

public class RequestWithdraw {

    @SerializedName("withdrawal_request")
    public NewWithdraw newWithdraw;
}
