package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Request.RequestWithdraw;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.WithdrawResponse;
import br.com.tratto.Model.Withdraw;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 10/7/17.
 */

public class WithdrawManager {

    private static String TAG = "ConvertManager";

    private Context context;
    private WithdrawCallback callback;

    public interface WithdrawCallback extends BaseCallback {
        void onSuccess(Withdraw withdraw);
    }

    public WithdrawManager(Context context, WithdrawCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createWithdrawRequest(RequestWithdraw requestWithdraw) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.createWithdrawRequest(token, requestWithdraw).enqueue(new Callback<WithdrawResponse>() {
            @Override
            public void onResponse(Call<WithdrawResponse> call, Response<WithdrawResponse> response) {
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
                        callback.onSuccess(response.body().withdraw);
                    }
                }
            }

            @Override
            public void onFailure(Call<WithdrawResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
