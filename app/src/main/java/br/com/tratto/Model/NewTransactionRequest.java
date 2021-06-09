package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/30/17.
 */

public class NewTransactionRequest {

    @SerializedName("from_profile_id")
    public int fromProfileId;

    @SerializedName("to_profile_id")
    public int toProfileId;

    @SerializedName("request_description")
    public String description;

    @SerializedName("amount")
    public double amount;

//    @SerializedName("rapd_transaction_type")
//    public String type;

    @SerializedName("status")
    public String status;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("currency_id")
    public int currencyId;

}
