package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.BankAccount;
import br.com.tratto.Model.Response.BankAccountResponse;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 10/5/17.
 */

public class BankAccountManager {

    private String TAG = "BankAccountManager";
    private Context context;
    private BankAccountCallback callback;

    public interface BankAccountCallback extends BaseCallback {
        void onSuccess(ArrayList<BankAccount> result);
    }

    public BankAccountManager(Context context, BankAccountCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getAllBankAccounts() {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.getAllBankAccounts(token).enqueue(new Callback<BankAccountResponse>() {
            @Override
            public void onResponse(Call<BankAccountResponse> call, Response<BankAccountResponse> response) {
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
                        callback.onSuccess(response.body().bankAccounts);
                    }
                }
            }

            @Override
            public void onFailure(Call<BankAccountResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
