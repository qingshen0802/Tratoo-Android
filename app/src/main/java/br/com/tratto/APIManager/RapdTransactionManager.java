package br.com.tratto.APIManager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.RapdTransation;
import br.com.tratto.Model.Request.RequestRapdTransactionRequest;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.RapdTransactionResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 9/6/17.
 */

public class RapdTransactionManager {

    private String TAG = "RapdTransactionManager";
    private Context context;
    private RapdTransactionCallback callback;

    public interface RapdTransactionCallback extends BaseCallback {
        void onCreate(@Nullable RapdTransation rapdTransation);
        void onSuccess(ArrayList<RapdTransation> result);
    }

    public RapdTransactionManager(Context context, RapdTransactionCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createRapdTransaction(RequestRapdTransactionRequest requestRapdTransactionRequest) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.createRapdTransaction(token, requestRapdTransactionRequest).enqueue(new Callback<RapdTransactionResponse>() {
            @Override
            public void onResponse(Call<RapdTransactionResponse> call, Response<RapdTransactionResponse> response) {
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
                        RapdTransactionResponse transactionResponse = response.body();
                        if (transactionResponse == null) {
                            callback.onFailed("Server error");
                            return;
                        }
                        if (transactionResponse.errorsIds.isEmpty()) {
                            callback.onCreate(transactionResponse.rapdTransation);
                        } else {
                            List<Object> errors = transactionResponse.errorsIds.get(0);
                            for (Object o : errors) {
                                try {
                                    String er = (String) o;
                                    callback.onFailed(er);
                                    break;
                                } catch (ClassCastException ignored) {
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RapdTransactionResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }

    public void getAllRapdTransactions(int walletId) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.getRapdTransactions(token, walletId).enqueue(new Callback<RapdTransactionResponse>() {
            @Override
            public void onResponse(Call<RapdTransactionResponse> call, Response<RapdTransactionResponse> response) {
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
                        callback.onSuccess(response.body().rapdTransations);
                    }
                }
            }

            @Override
            public void onFailure(Call<RapdTransactionResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
