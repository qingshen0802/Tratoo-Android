package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/7/17.
 */

public class NewWithdraw {
//    "user_id": 70,
//            "profile_id": 64,
//            "currency_id": 1,
//            "wallet_id": 219,
//            "amount": 168.08,
//            "status": "pending",
//            "request_type": "bank_transfer",
//            "request_description": ""

    @SerializedName("user_id")
    public int userId;

    @SerializedName("profile_id")
    public int profileId;

    @SerializedName("currency_id")
    public int currencyId;

    @SerializedName("wallet_id")
    public int walletId;

    @SerializedName("amount")
    public double amount;

    @SerializedName("status")
    public String status;

    @SerializedName("request_type")
    public String requestType;

    @SerializedName("request_description")
    public String description;
}
