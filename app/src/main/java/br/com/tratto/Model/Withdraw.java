package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/7/17.
 */

public class Withdraw {
    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("profile_id")
    public int profileId;

    @SerializedName("wallet_id")
    public int walletId;

    @SerializedName("currency_id")
    public int currencyId;

    @SerializedName("amount")
    public double amount;

    @SerializedName("status")
    public String status;

    @SerializedName("request_description")
    public String requestDescription;

    @SerializedName("request_type")
    public String requestType;

    @SerializedName("prepaid_card_delivery_address")
    public String prepaidCardAddress;

    @SerializedName("exchange_to_default_currency")
    public String exchangeDefaultCurrency;

    @SerializedName("human_amount")
    public String humanAmount;

}
