package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 9/27/17.
 */

public class NewRapdTransaction {

    @SerializedName("to_profile_id")
    public int toProfileId;

    @SerializedName("from_profile_id")
    public int fromProfileId;

    @SerializedName("transaction_description")
    public String description;

    @SerializedName("from_wallet_id")
    public int fromWalletId;

    @SerializedName("amount")
    public double amount;

    @SerializedName("rapd_transaction_type")
    public String type;
}
