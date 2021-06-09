package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lily on 9/6/17.
 */

public class RapdTransation {

    @SerializedName("id")
    public String id;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("profile_id")
    public int profileId;
    @SerializedName("wallet_id")
    public int walletId;
    @SerializedName("from_profile_id")
    public int fromProfileId;
    @SerializedName("to_profile_id")
    public int toProfileId;
    @SerializedName("amount")
    public double amount;
    @SerializedName("from_wallet_id")
    public int fromWalletId;
    @SerializedName("to_wallet_id")
    public int toWalletId;
    @SerializedName("rapd_transaction_type")
    public String type;
    @SerializedName("transaction_request_id")
    public int transactionRequestId;
    @SerializedName("transactionable_type")
    public String transactionableType;
    @SerializedName("currency_id")
    public int currencyId;
    @SerializedName("human_amount")
    public String humanAmount;
    @SerializedName("human_timestamp")
    public String humanTimeStamp;
    @SerializedName("to_profile")
    public Profile toProfile;
    @SerializedName("from_profile")
    public Profile fromProfile;
    @SerializedName("wallet")
    public Wallet wallet;
    @SerializedName("created_at")
    public Date createdAt;
}
