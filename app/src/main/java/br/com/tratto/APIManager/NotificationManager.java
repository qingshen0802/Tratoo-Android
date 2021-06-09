package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Notification;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.NotificationResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 8/30/17.
 */

public class NotificationManager {

    private static String TAG = "Notification Manager";

    private Context context;
    private NotificationCallback callback;

    public interface NotificationCallback extends BaseCallback {
        void onSuccess(ArrayList<Notification> result);
    }

    public NotificationManager(Context context, NotificationCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void getAllNotifications() {
        String token = UtilFunctions.getCurrentUser(context).accessToken;
        Log.d(TAG, token);
        apiInterface.getAllNotification(token).enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
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
                        callback.onSuccess(response.body().notifications);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (callback != null) {
                    callback.onFailed(t.getMessage());
                }
            }
        });
    }
}
