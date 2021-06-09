package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.Withdraw;

/**
 * Created by lily on 10/7/17.
 */

public class WithdrawResponse extends ResponseModel {

    @SerializedName("withdrawal_request")
    public Withdraw withdraw;
}
