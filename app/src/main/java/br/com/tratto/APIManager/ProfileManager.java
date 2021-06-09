package br.com.tratto.APIManager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.PeopleResponse;
import br.com.tratto.Model.Response.ProfileResponse;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 8/24/17.
 */

public class ProfileManager {

    private String TAG = "ProfileManager";
    private Context context;
    private ProfileManagerCallback apiCallback;

    public interface ProfileManagerCallback extends BaseCallback {
        void onSuccess(ArrayList<Profile> profiles, boolean isLastPage, int page);
    }

    public ProfileManager(Context context, ProfileManagerCallback callback) {
        this.context = context;
        this.apiCallback = callback;
    }

    public void getAllProfile() {
        apiInterface.getAllProfile(UtilFunctions.getCurrentUser(context).accessToken).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (apiCallback != null) {
                    if (response.errorBody() != null) {
                        try {
                            Gson gson = new Gson();
                            ErrorResponse error;
                            error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiCallback.onFailed(error.message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        apiCallback.onSuccess(response.body().profiles, true, response.body().page);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (apiCallback != null) {
                    apiCallback.onFailed(t.getMessage());
                }
            }
        });
    }

    public void searchPeople(int page) {
        Callback<PeopleResponse> callback = new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                if (apiCallback != null) {
                    if (response.errorBody() != null) {
                        try {
                            Gson gson = new Gson();
                            ErrorResponse error;
                            error = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            apiCallback.onFailed(error.message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (response.body().profiles.size() < response.body().perPage) {
                            apiCallback.onSuccess(response.body().profiles, true, response.body().page);
                        } else {
                            apiCallback.onSuccess(response.body().profiles, false, response.body().page);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                if (apiCallback != null) {
                    apiCallback.onFailed(t.getMessage());
                }
            }
        };
        apiInterface.searchPeople(UtilFunctions.getCurrentUser(context).accessToken, page).enqueue(callback);
//        switch (keyType) {
//            case Constants.SEARCH_KEY_USERNAME:
//                apiInterface.searchPeople(UtilFunctions.getCurrentUser(context).accessToken, keyName, "", "").enqueue(callback);
//                break;
//            case Constants.SEARCH_KEY_EMAIL:
//                apiInterface.searchPeople(UtilFunctions.getCurrentUser(context).accessToken, "", keyName, "").enqueue(callback);
//                break;
//            case Constants.SEARCH_KEY_PHONENUMBER:
//                apiInterface.searchPeople(UtilFunctions.getCurrentUser(context).accessToken, "", "", keyName).enqueue(callback);
//                break;
//        }
    }
}
