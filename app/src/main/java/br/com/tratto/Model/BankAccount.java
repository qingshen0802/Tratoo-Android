package br.com.tratto.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lily on 10/5/17.
 */

public class BankAccount {

    @SerializedName("id")
    public String id;
    @SerializedName("branch_number")
    public String branchNumber;
    @SerializedName("account_number")
    public String accountNumber;
    @SerializedName("account_type")
    public String accountType;
    @SerializedName("profile_id")
    public int profileId;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("branch_digit")
    public String branchDigit;
    @SerializedName("account_digit")
    public String accountDigit;
    @SerializedName("is_system_account")
    public boolean isSystemAccount;
    @SerializedName("bank_number")
    public int bankNumber;
    @SerializedName("bank_name_id")
    public int bankNameId;
    @SerializedName("bank_name")
    public String bankName;
}
