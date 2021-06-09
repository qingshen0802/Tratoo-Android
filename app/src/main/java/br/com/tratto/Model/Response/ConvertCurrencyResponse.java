package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/5/17.
 */

public class ConvertCurrencyResponse  extends ResponseModel{

    @SerializedName("from")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("from_amount")
    public double fromAmount;
    @SerializedName("to_amount")
    public double toAmount;
}
