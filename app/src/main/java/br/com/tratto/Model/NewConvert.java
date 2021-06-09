package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/5/17.
 */

public class NewConvert {

    @SerializedName("profile_id")
    public int profileId;

    @SerializedName("from_amount")
    public double fromAmount;

    @SerializedName("to_amount")
    public double toAmount;

    @SerializedName("from_currency_id")
    public int fromCurrencyId;

    @SerializedName("to_currency_id")
    public int toCurrencyId;
}
