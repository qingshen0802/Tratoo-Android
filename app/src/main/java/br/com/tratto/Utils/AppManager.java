package br.com.tratto.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import br.com.tratto.APIManager.APIClient;
import br.com.tratto.Interface.APIInterface;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Wallet;


/**
 * Created by lily on 8/24/17.
 */

public class AppManager extends Application {

    private static AppManager instance;
    public static Context context;

    private static final String MyPREFERENCES = "MyPrefs";
    private static SharedPreferences _sharedPreference = null;

    public static String userId = "";
    public static String gUserCPF, gUserName, gUserEmail;

    public static APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    public static ArrayList<Wallet> wallets = new ArrayList<>();
    public static ArrayList<Profile> profiles = new ArrayList<>();

    public static AppManager sharedInstance(Context context) {
        if ( context != null )
            AppManager.context = context;

        if ( instance == null ) {
            instance = new AppManager();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public SharedPreferences get_sharedPreference() {
        if ( _sharedPreference == null ) {
            if ( context == null )
                return null;

            _sharedPreference = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        }
        return _sharedPreference;
    }

    public void removeValue(String key) {
        if ( get_sharedPreference() == null )
            return;

        SharedPreferences.Editor editor = get_sharedPreference().edit();
        editor.remove(key);
        editor.commit();
    }

    public String getStringValue(String key) {
        if ( get_sharedPreference() == null )
            return null;
        return get_sharedPreference().getString(key, "");
    }

    public void setStringValue(String key, String value) {
        if ( get_sharedPreference() == null )
            return;

        if ( value == null || value.isEmpty() ) {
            removeValue(key);
        }
        else {
            SharedPreferences.Editor editor = get_sharedPreference().edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public int getIntValue(String key) {
        if ( get_sharedPreference() == null )
            return Integer.MIN_VALUE;
        return get_sharedPreference().getInt(key, Integer.MIN_VALUE);
    }

    public void setIntValue(String key, int value) {
        if ( get_sharedPreference() == null )
            return;
        SharedPreferences.Editor editor = get_sharedPreference().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void clearValues() {
        SharedPreferences.Editor editor = get_sharedPreference().edit();
        editor.clear();
        editor.commit();
    }
}
