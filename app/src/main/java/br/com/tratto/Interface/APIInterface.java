package br.com.tratto.Interface;

import br.com.tratto.Model.Request.RequestConversion;
import br.com.tratto.Model.Request.RequestRapdTransactionRequest;
import br.com.tratto.Model.Request.RequestNewUser;
import br.com.tratto.Model.Request.RequestTransactionRequest;
import br.com.tratto.Model.Request.RequestUpdatePassword;
import br.com.tratto.Model.Request.RequestWithdraw;
import br.com.tratto.Model.Response.BankAccountResponse;
import br.com.tratto.Model.Response.ConversionResponse;
import br.com.tratto.Model.Response.ConvertCurrencyResponse;
import br.com.tratto.Model.Response.CurrencyResponse;
import br.com.tratto.Model.Response.TransactionRequestResponse;
import br.com.tratto.Model.Response.WithdrawResponse;
import br.com.tratto.Model.User;
import br.com.tratto.Model.Response.BusinessResponse;
import br.com.tratto.Model.Response.NewUserResponse;
import br.com.tratto.Model.Response.NotificationResponse;
import br.com.tratto.Model.Response.PeopleResponse;
import br.com.tratto.Model.Response.ProfileResponse;
import br.com.tratto.Model.Response.RapdTransactionResponse;
import br.com.tratto.Model.Response.UserResponse;
import br.com.tratto.Model.Response.WalletResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lily on 9/5/17.
 */

public interface APIInterface {

    @FormUrlEncoded
    @POST("/api/tokens")
    Call<UserResponse> login(@Field("email") String email, @Field("password") String password, @Field("device_number") String deviceNumber);

    @GET("/api/users/resend_confirmation_sms")
    Call<UserResponse> phoneVerify(@Query("user_id") String userId);

    @GET("/api/tokens/login_via_sms")
    Call<UserResponse> logInWithSMS(@Query("user_id") String userId, @Query("sms_confirmation_code") String smsCode, @Query("device_number") String deviceId);

    @GET("/api/users/reset_password_sms_confirmation")
    Call<UserResponse> requestResetPassword(@Query("phone_number") String phoneNumber);

    @GET("/api/users/password_reset_confirm_via_sms")
    Call<UserResponse> confirmSMSResetPassword(@Query("id") String id, @Query("sms_confirmation_code") String smscode);

    @PATCH("/api/users/{id}/update_password")
    Call<String> updatePassword(@Path("id") String id, @Body RequestUpdatePassword user);

    @GET("/api/profiles")
    Call<ProfileResponse> getAllProfile(@Header("Authorization") String token);

    @GET("/api/wallets")
    Call<WalletResponse> getAllWallets(@Header("Authorization") String token, @Query("profile_id") int profileId);

    @GET("/api/companies")
    Call<BusinessResponse> getAllBusiness(@Header("Authorization") String token);

    @GET("/api/notifications")
    Call<NotificationResponse> getAllNotification(@Header("Authorization") String token);

    @GET("/api/rapd_transactions")
    Call<RapdTransactionResponse> getRapdTransactions(@Header("Authorization") String token, @Query("wallet_id") int walletId);

    @POST("/api/users")
    Call<NewUserResponse> createUser(@Body RequestNewUser user);

    @GET("/api/people")
//    Call<PeopleResponse> searchPeople(@Header("Authorization") String token, @Query("search[name]") String name, @Query("search[email]") String email, @Query("search[phone_number]") String phoneNumber);
    Call<PeopleResponse> searchPeople(@Header("Authorization") String token, @Query("page") int page);

    @POST("/api/rapd_transactions")
    Call<RapdTransactionResponse> createRapdTransaction(@Header("Authorization") String token, @Body RequestRapdTransactionRequest requestRapdTransactionRequest);

    @POST("/api/transaction_requests")
    Call<TransactionRequestResponse> createTransactionRequest(@Header("Authorization") String token, @Body RequestTransactionRequest requestTransactionRequest);

    @GET("/api/currencies")
    Call<CurrencyResponse> getAllCurrencies();

    @GET("/api/bank_accounts")
    Call<BankAccountResponse> getAllBankAccounts(@Header("Authorization") String token);

    @GET("/api/currencies/convert")
    Call<ConvertCurrencyResponse> convertCurrency(@Query("from_currency_id") int fromCurrencyId, @Query("to_currency_id") int toCurrencyId, @Query("amount") double amount, @Query("buying") boolean buying, @Query("needs_normalization") boolean needNormalization);

    @POST("/api/conversion_requests")
    Call<ConversionResponse> createConvertRequest(@Header("Authorization") String token, @Body RequestConversion conversion);

    @POST("/api/withdrawal_requests")
    Call<WithdrawResponse> createWithdrawRequest(@Header("Authorization") String token, @Body RequestWithdraw requestWithdraw);
}
