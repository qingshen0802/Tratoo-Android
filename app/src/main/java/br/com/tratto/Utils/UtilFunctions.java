package br.com.tratto.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Interface.DialogButtonCallback;
import br.com.tratto.ListAdapter.ProfileListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 8/23/17.
 */

public class UtilFunctions {

    private static Dialog loadingDialog;

    public static boolean isPasswordCorrect(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*");
    }

    public static void showlLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public static void showMessageDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public static void showProfileChooseDialog(Context context, ArrayList<Profile> profiles, final DialogButtonCallback callback) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_profile);

        final FontButton btnContinue = (FontButton) dialog.findViewById(R.id.btn_dialog_choose_profile_continue);
        final ListView profileList = (ListView) dialog.findViewById(R.id.list_choose_profiles);

        final ProfileListAdapter profileListAdapter = new ProfileListAdapter(context, profiles);
        profileList.setAdapter(profileListAdapter);
        profileList.setSelection(0);
        profileList.setItemsCanFocus(true);
        final int[] index = {0};

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onContinue(profileListAdapter.getItem(index[0]));
                dialog.dismiss();
            }
        });

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index[0] = position;
                view.setSelected(true);
            }
        });

        dialog.show();
    }

    public static void setCurrentUser(Context context, User user) {
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        AppManager.sharedInstance(context).setStringValue("current_user", userString);
    }

    public static User getCurrentUser(Context context) {
        Gson gson = new Gson();
        String json = AppManager.sharedInstance(context).getStringValue("current_user");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public static void setCurrentProfile(Context context, Profile profile) {
        Gson gson = new Gson();
        String profileString = gson.toJson(profile);
        AppManager.sharedInstance(context).setStringValue("current_profile", profileString);
    }

    public static Profile getCurrentProfile(Context context) {
        Gson gson = new Gson();
        String json = AppManager.sharedInstance(context).getStringValue("current_profile");
        Profile profile = gson.fromJson(json, Profile.class);
        return profile;
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat dest = new SimpleDateFormat("dd MMM yyyy hh:mm", Locale.ENGLISH);
        return dest.format(date);
    }
}
