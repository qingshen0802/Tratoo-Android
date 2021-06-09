package br.com.tratto.APIManager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.tratto.Interface.BaseCallback;
import br.com.tratto.Model.PasswordForm;
import br.com.tratto.Model.Request.RequestNewUser;
import br.com.tratto.Model.Request.RequestUpdatePassword;
import br.com.tratto.Model.Response.ErrorResponse;
import br.com.tratto.Model.Response.NewUserResponse;
import br.com.tratto.Model.Response.UserResponse;
import br.com.tratto.Model.User;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.tratto.Utils.AppManager.apiInterface;

/**
 * Created by lily on 9/1/17.
 */

public class UserManager {

    private String TAG = "UserManager";
    private Context context;
    private UserCallback callback;

    public interface UserCallback extends BaseCallback {
        void onSuccess(@Nullable User user);
    }

    public UserManager(Context context, UserCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void phoneVerify(String userId) {
        apiInterface.phoneVerify(userId).enqueue(new Callback<UserResponse>() {
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
                        callback.onSuccess(null);
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

    public void loginWithSMS(String userId, String smsCode, String deviceId) {
        apiInterface.logInWithSMS(userId, smsCode, deviceId).enqueue(new Callback<UserResponse>() {
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
                        callback.onSuccess(response.body().user);
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

    public void requestForgotPassword(String phoneNumber) {
        apiInterface.requestResetPassword(phoneNumber).enqueue(new Callback<UserResponse>() {
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
                        AppManager.userId = response.body().userId;
                        callback.onSuccess(null);
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

    public void confirmSMSResetPassword(String userId, String smsCode) {
        apiInterface.confirmSMSResetPassword(userId, smsCode).enqueue(new Callback<UserResponse>() {
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
                        AppManager.userId = response.body().userId;
                        callback.onSuccess(null);
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

    public void createUser(RequestNewUser user) {
        apiInterface.createUser(user).enqueue(new Callback<NewUserResponse>() {
            @Override
            public void onResponse(Call<NewUserResponse> call, Response<NewUserResponse> response) {
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
                        callback.onSuccess(null);
                        AppManager.userId = response.body().id;
                    }
                }
            }

            @Override
            public void onFailure(Call<NewUserResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
                Log.d(TAG, call.toString());
            }
        });
    }

    public void updatePassword(String password, String userId) {
        PasswordForm user = new PasswordForm();
        user.password = password;
        user.passwordConfirmation = password;
        apiInterface.updatePassword(userId, new RequestUpdatePassword(user)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                UtilFunctions.dismissLoadingDialog();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d(TAG, t.toString());
                Log.d(TAG, call.toString());
                UtilFunctions.dismissLoadingDialog();
            }
        });
    }
}
