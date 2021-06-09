package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 8/24/17.
 */

public class Wallet {

    @SerializedName("id")
    public int id;
    @SerializedName("profile_id")
    public int profileId;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("balance")
    public double balance;
    @SerializedName("human_balance")
    public String humanBalance;
    @SerializedName("is_default")
    public boolean isDefault;
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
    @SerializedName("wallet_type")
    public WalletType walletType;

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", profileId=" + profileId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", humanBalance='" + humanBalance + '\'' +
                '}';
    }
}
