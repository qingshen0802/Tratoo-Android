package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.tratto.Model.Wallet;

/**
 * Created by lily on 9/6/17.
 */

public class WalletResponse extends ResponseModel{

    @SerializedName("wallets")
    public ArrayList<Wallet> wallets;
}
