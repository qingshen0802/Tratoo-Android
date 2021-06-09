package br.com.tratto.Model.Response;

import com.google.gson.annotations.SerializedName;

import br.com.tratto.Model.Convert;

/**
 * Created by lily on 10/5/17.
 */

public class ConversionResponse extends ResponseModel {

    @SerializedName("conversion_request")
    public Convert convert;
}
