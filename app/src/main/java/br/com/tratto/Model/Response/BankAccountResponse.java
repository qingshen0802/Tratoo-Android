package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.BankAccount;

/**
 * Created by lily on 10/5/17.
 */

public class BankAccountResponse extends ResponseModel {

    @SerializedName("bank_accounts")
    public ArrayList<BankAccount> bankAccounts;
}
