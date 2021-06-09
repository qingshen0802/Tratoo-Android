package br.com.tratto.APIManager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Request.RequestTransactionRequest;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.TransactionRequestResponse;
import br.com.tratto.Model.TransactionRequest;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 9/30/17.
 */

public class TransactionRequestManager {

    private String TAG = "TransactRequestManager";
    private Context context;
    private TransactionRequestCallback callback;

    public interface TransactionRequestCallback extends BaseCallback {
        void onCreate(@Nullable TransactionRequest transactionRequest);
//        void onSuccess(ArrayList<TransactionRequest> result);
    }

    public TransactionRequestManager(Context context, TransactionRequestCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createTransactionRequest(RequestTransactionRequest transactionRequest) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.createTransactionRequest(token, transactionRequest).enqueue(new Callback<TransactionRequestResponse>() {
            @Override
            public void onResponse(Call<TransactionRequestResponse> call, Response<TransactionRequestResponse> response) {
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
                        callback.onCreate(response.body().transactionRequest);
                    }
                }
            }

            @Override
            public void onFailure(Call<TransactionRequestResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
