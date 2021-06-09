package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.TransactionRequest;

/**
 * Created by lily on 9/30/17.
 */

public class TransactionRequestResponse extends ResponseModel {

    @SerializedName("transaction_requests")
    public ArrayList<TransactionRequest> transactionRequests;

    @SerializedName("transaction_request")
    public TransactionRequest transactionRequest;
}
