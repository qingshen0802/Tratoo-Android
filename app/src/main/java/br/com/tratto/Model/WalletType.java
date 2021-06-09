package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.Currency;

/**
 * Created by lily on 8/25/17.
 */

public class WalletType {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("withdrawable")
    public boolean withdrawable;
    @SerializedName("currency_id")
    public int currencyId;
    @SerializedName("color")
    public String color;
    @SerializedName("conversionable")
    public boolean conversionable;
    @SerializedName("loadable")
    public boolean loadable;
    @SerializedName("max_load_per_day")
    public double maxLoadDay;
    @SerializedName("max_load_per_week")
    public double maxLoadWeek;
    @SerializedName("max_load_per_month")
    public double maxLoadMonth;
    @SerializedName("max_withdraw_per_day")
    public double maxWithdrawDay;
    @SerializedName("max_withdraw_per_week")
    public double maxWithdrawWeek;
    @SerializedName("max_withdraw_per_month")
    public double maxWithdrawMonth;
    @SerializedName("max_transact_per_day")
    public double maxTransactDay;
    @SerializedName("max_transact_per_week")
    public double maxTransactWeek;
    @SerializedName("max_transact_per_month")
    public double maxTransactMonth;

    @SerializedName("currency")
    public Currency currency;

    @Override
    public String toString() {
        return "WalletType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", withdrawable=" + withdrawable +
                ", currencyId=" + currencyId +
                ", color='" + color + '\'' +
                ", conversionable=" + conversionable +
                ", loadable=" + loadable +
                ", maxLoadDay=" + maxLoadDay +
                ", maxLoadWeek=" + maxLoadWeek +
                ", maxLoadMonth=" + maxLoadMonth +
                ", maxWithdrawDay=" + maxWithdrawDay +
                ", maxWithdrawWeek=" + maxWithdrawWeek +
                ", maxWithdrawMonth=" + maxWithdrawMonth +
                ", maxTransactDay=" + maxTransactDay +
                ", maxTransactWeek=" + maxTransactWeek +
                ", maxTransactMonth=" + maxTransactMonth +
                ", currency=" + currency +
                '}';
    }
}
