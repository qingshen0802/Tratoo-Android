package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/5/17.
 */

public class Convert {

    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("profile_id")
    public int profileId;

    @SerializedName("from_wallet_id")
    public int fromWalletId;

    @SerializedName("to_wallet_id")
    public int toWalletId;

    @SerializedName("from_amount")
    public double fromAmount;

    @SerializedName("to_amount")
    public double toAmount;

    @SerializedName("status")
    public String status;
}
