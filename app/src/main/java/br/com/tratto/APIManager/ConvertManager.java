package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Convert;
import br.com.tratto.Model.Request.RequestConversion;
import br.com.tratto.Model.Response.ConversionResponse;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 10/5/17.
 */

public class ConvertManager {

    private static String TAG = "ConvertManager";

    private Context context;
    private ConvertCallback callback;

    public interface ConvertCallback extends BaseCallback {
        void onSuccess(Convert convert);
    }

    public ConvertManager(Context context, ConvertCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void createConversionRequest(RequestConversion conversion) {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        apiInterface.createConvertRequest(token, conversion).enqueue(new Callback<ConversionResponse>() {
            @Override
            public void onResponse(Call<ConversionResponse> call, Response<ConversionResponse> response) {
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
                        callback.onSuccess(response.body().convert);
                    }
                }
            }

            @Override
            public void onFailure(Call<ConversionResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
