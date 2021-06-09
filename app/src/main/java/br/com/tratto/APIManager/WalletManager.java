package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Wallet;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.WalletResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 8/25/17.
 */

public class WalletManager {

    private String TAG = "WalletManager";
    private Context context;
    private WalletCallback callback;

    public interface WalletCallback extends BaseCallback {
        void onSuccess(ArrayList<Wallet> result);
    }

    public WalletManager(Context context, WalletCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getAllWallets(int profileId) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        Log.d(TAG, token);
        apiInterface.getAllWallets(token, profileId).enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
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
                        callback.onSuccess(response.body().wallets);
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
