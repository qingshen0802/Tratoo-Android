package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/30/17.
 */

public class TransactionRequest {
    @SerializedName("id")
    public String id;
    @SerializedName("from_profile_id")
    public int fromProfileId;
    @SerializedName("to_profile_id")
    public int toProfileId;
    @SerializedName("request_description")
    public String description;
    @SerializedName("amount")
    public double amount;
    @SerializedName("status")
    public String status;
    @SerializedName("currency_id")
    public int currencyId;
    @SerializedName("human_amount")
    public String humanAmount;
    @SerializedName("to_profile")
    public Profile toProfile;
}
