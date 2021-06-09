package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.NewRapdTransaction;

/**
 * Created by lily on 9/27/17.
 */

public class RequestRapdTransactionRequest {

    @SerializedName("rapd_transaction")
    public NewRapdTransaction rapdTransaction;
}
