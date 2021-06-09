package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Currency;

/**
 * Created by lily on 9/30/17.
 */

public class CurrencyResponse extends ResponseModel {

    @SerializedName("currencies")
    public ArrayList<Currency> currencies;
}
