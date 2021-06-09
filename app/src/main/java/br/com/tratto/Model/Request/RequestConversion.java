package br.com.tratto.Model.Request;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.NewConvert;

/**
 * Created by lily on 10/5/17.
 */

public class RequestConversion {

    @SerializedName("conversion_request")
    public NewConvert convert;
}
