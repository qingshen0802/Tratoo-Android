package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lily on 8/25/17.
 */

public class Currency {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("is_default")
    public boolean isDefault;
    @SerializedName("short_name")
    public String shortName;
    @SerializedName("currency_code")
    public String currencyCode;
    @SerializedName("insurance_amount")
    public String insuranceAmount;
    @SerializedName("country")
    public String country;

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                ", shortName='" + shortName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", insuranceAmount='" + insuranceAmount + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
