package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Response.BusinessResponse;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 8/30/17.
 */

public class BusinessManager {

    private String TAG = "BusinessManager";
    private Context context;
    private BusinessCallback callback;

    public interface BusinessCallback extends BaseCallback {
        void onSuccess(ArrayList<Profile> result);
    }

    public BusinessManager(Context context, BusinessCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getAllBusiness() {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        Log.d(TAG, token);
        apiInterface.getAllBusiness(token).enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
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
                        callback.onSuccess(response.body().business);
                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
