package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Currency;
import br.com.tratto.Model.Response.ConvertCurrencyResponse;
import br.com.tratto.Model.Response.CurrencyResponse;
import br.com.tratto.Model.Response.ErrorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 9/30/17.
 */

public class CurrencyManager {

    private static String TAG = "CurrencyManager";

    private Context context;
    private CurrencyCallback callback;

    public interface CurrencyCallback extends BaseCallback {
        void onGetAllCurrency(ArrayList<Currency> currencies);
        void onConvert(double convertedAmount);
    }

    public CurrencyManager(Context context, CurrencyCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getAllCurrencies() {
        apiInterface.getAllCurrencies().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                if (callback != null) {
                    if (response.errorBody() != null) {
                        try {
                            Gson gson = new Gson();
                            ErrorResponse error;
                            error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            callback.onFailed(error.message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        callback.onGetAllCurrency(response.body().currencies);
                    }
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }

    public void convertCurrency(int fromCurrencyId, int toCurrencyId, double amount) {
        apiInterface.convertCurrency(fromCurrencyId, toCurrencyId, amount, false, true).enqueue(new Callback<ConvertCurrencyResponse>() {
            @Override
            public void onResponse(Call<ConvertCurrencyResponse> call, Response<ConvertCurrencyResponse> response) {
                if (callback != null) {
                    if (response.errorBody() != null) {
                        try {
                            Gson gson = new Gson();
                            ErrorResponse error;
                            error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            callback.onFailed(error.message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        callback.onConvert(response.body().toAmount);
                    }
                }
            }

            @Override
            public void onFailure(Call<ConvertCurrencyResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
