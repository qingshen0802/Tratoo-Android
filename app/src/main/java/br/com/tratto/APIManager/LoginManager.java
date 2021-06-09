package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.tratto.Interface.APIInterface;
import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 8/23/17.
 */

public class LoginManager{

    private String TAG = "LoginManager";
    private Context context;
    private LoginCallback callback;

    public LoginManager(Context context, LoginCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void login(String email, String password, String deviceId) {

        apiInterface.login(email, password, deviceId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
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
                        callback.onSuccess(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }

    public interface LoginCallback extends BaseCallback {
        void onSuccess(UserResponse response);
    }
}

