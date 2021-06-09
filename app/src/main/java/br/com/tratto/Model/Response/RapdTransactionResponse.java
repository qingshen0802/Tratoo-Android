package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import br.com.tratto.Model.RapdTransation;

/**
 * Created by lily on 9/6/17.
 */

public class RapdTransactionResponse extends ResponseModel {

    @SerializedName("rapd_transactions")
    public ArrayList<RapdTransation> rapdTransations;

    @SerializedName("rapd_transaction")
    public RapdTransation rapdTransation;

    @SerializedName("errors_ids")
    public List<List<Object>> errorsIds = new ArrayList<>();
}
