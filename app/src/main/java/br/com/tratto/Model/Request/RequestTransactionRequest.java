package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.NewTransactionRequest;

/**
 * Created by lily on 9/30/17.
 */

public class RequestTransactionRequest {

    @SerializedName("transaction_request")
    public NewTransactionRequest transaction;
}
