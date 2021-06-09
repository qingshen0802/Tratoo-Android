package br.com.tratto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Stack;

import br.com.tratto.APIManager.ProfileManager;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.Business.BusinessFragment;
import br.com.tratto.Fragment.DashBoard.MainFragment;
import br.com.tratto.Fragment.DashBoard.WalletDetailFragment;
import br.com.tratto.Fragment.Notification.NotificationFragment;
import br.com.tratto.Fragment.Profile.EditProfileFragment;
import br.com.tratto.Fragment.Profile.ProfileFragment;
import br.com.tratto.Fragment.Service.ManageWalletFragment;
import br.com.tratto.Interface.DialogButtonCallback;
import br.com.tratto.ListAdapter.NavProfileListAdapter;
import br.com.tratto.ListAdapter.ProfileListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ProfileManager.ProfileManagerCallback, DialogButtonCallback {

    private static String TAG = "MainAcitivity";
    private Fragment currentFragment;
    private Profile currentProfile;

    private CircleImageView profileImageView;
    private FontTextView txtFullName, txtuserName;

    private ImageView btnDrawerToggle, btnBack, btnAdd, btnUpload, btnClose, btnCamera, btnFilter, btnSearch, btnSettings;
    private FontTextView txtTitle;
    private EditText edtSearchText;
    private ImageView btnDropDown;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private Stack<String> mFragmentStack;

    private ProfileManager profileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDrawerToggle = findViewById(R.id.btn_nav_menu);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        profileImageView = navigationView.getHeaderView(0).findViewById(R.id.side_menu_profile_image);
        txtFullName = navigationView.getHeaderView(0).findViewById(R.id.text_side_menu_fullname);
        txtuserName = navigationView.getHeaderView(0).findViewById(R.id.text_side_menu_username);
        btnDropDown = navigationView.getHeaderView(0).findViewById(R.id.btn_side_menu_drop_down);

        btnAdd = findViewById(R.id.btn_nav_add);
        btnBack = findViewById(R.id.btn_nav_back);
        btnUpload = findViewById(R.id.btn_nav_upload);
        btnClose = findViewById(R.id.btn_nav_close);
        btnCamera = findViewById(R.id.btn_nav_camera);
        btnFilter = findViewById(R.id.btn_nav_filter);
        btnSearch = findViewById(R.id.btn_nav_search);
        btnSettings = findViewById(R.id.btn_nav_settings);

        txtTitle = findViewById(R.id.text_nav_title);
        edtSearchText = (FontEditText) findViewById(R.id.edt_nav_search);

        profileManager = new ProfileManager(this, this);
        if (AppManager.profiles.size() < 1) {
            profileManager.getAllProfile();
        }

        btnDropDown.setOnClickListener(this);

        MainFragment mainFragment = MainFragment.newInstance();
        initHeader();
        initUI();
        init(mainFragment);
    }

    private void initUI() {
        btnSearch.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnDrawerToggle.setOnClickListener(this);
    }

    private void initHeader() {
        currentProfile = UtilFunctions.getCurrentProfile(this);
        if (!currentProfile.photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(this).load(currentProfile.photoUrl).into(profileImageView);
        }
        txtFullName.setText(currentProfile.fullName);
        txtuserName.setText("@" + currentProfile.userName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (this.getClass() != MainActivity.class || mFragmentStack.size() == 1) {
            super.onBackPressed();
        } else if (mFragmentStack != null) {
            if (mFragmentStack.size() > 1) {
                // Remove the fragment
                removeFragment();
                super.onBackPressed();
            }
        }
        if (mFragmentStack.size() > 1) {
            showBack(true);
        } else {
            showBack(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            MainFragment mainFragment = MainFragment.newInstance();
            switchFragment(mainFragment, true, getResources().getString(R.string.dashboard_title));
        } else if (id == R.id.nav_service) {
            ManageWalletFragment manageWalletFragment = ManageWalletFragment.newInstance();
            switchFragment(manageWalletFragment, true, getResources().getString(R.string.manage_title));
        } else if (id == R.id.nav_activity) {
            BusinessFragment businessFragment = BusinessFragment.newInstance();
            switchFragment(businessFragment, true, getResources().getString(R.string.business_title));
        } else if (id == R.id.nav_notification) {
            NotificationFragment notificationFragment = NotificationFragment.newInstance();
            switchFragment(notificationFragment, true, getResources().getString(R.string.notification_title));
        } else if (id == R.id.nav_profile) {
            ProfileFragment profileFragment = ProfileFragment.newInstance();
            switchFragment(profileFragment, true, "");
        } else if (id == R.id.nav_logout) {
            AppManager.sharedInstance(this).clearValues();
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init(Fragment fragment) {
        mFragmentStack = new Stack<>();
        switchFragment(fragment, true, getResources().getString(R.string.dashboard_title));
    }

    public void switchFragment(Fragment fragment, boolean isReplace, String title) {
        String tag = title;
        if (currentFragment == null || currentFragment.getClass() != fragment.getClass()) {
            Log.d(TAG, "Changing fragment");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!isReplace) {
                fragmentTransaction.replace(R.id.main_fragment_container, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
            } else {
                fragmentTransaction.replace(R.id.main_fragment_container, fragment, tag);
                mFragmentStack.clear();
            }
            mFragmentStack.add(tag);
            fragmentTransaction.commit();
            updateUI(fragment, title);
        }
        if (mFragmentStack.size() > 1) {
            showBack(true);
        } else {
            showBack(false);
        }
    }

    private void showBack(boolean isBack) {
        if (isBack) {
            btnBack.setVisibility(VISIBLE);
            btnDrawerToggle.setVisibility(GONE);
        } else {
            btnBack.setVisibility(GONE);
            btnDrawerToggle.setVisibility(VISIBLE);
        }
    }

    private void updateUI(Fragment fragment, String title) {
        currentFragment = fragment;
        txtTitle.setText(title);
        if (currentFragment.getClass() == MainFragment.class) {
            hideHeaderButtons();
            btnSettings.setVisibility(VISIBLE);
            txtTitle.setVisibility(VISIBLE);
        } else if (currentFragment.getClass() == WalletDetailFragment.class) {
            hideHeaderButtons();
            txtTitle.setVisibility(VISIBLE);
        } else if (currentFragment.getClass() == NotificationFragment.class || currentFragment.getClass() == BusinessFragment.class) {
            hideHeaderButtons();
            txtTitle.setVisibility(VISIBLE);
            btnFilter.setVisibility(VISIBLE);
            btnSearch.setVisibility(VISIBLE);
        } else if (currentFragment.getClass() == EditProfileFragment.class) {
            hideHeaderButtons();
            txtTitle.setVisibility(VISIBLE);
            btnCamera.setVisibility(VISIBLE);
        } else {
            hideHeaderButtons();
            txtTitle.setVisibility(VISIBLE);
        }
    }

    private void hideHeaderButtons() {
        edtSearchText.setVisibility(GONE);
        btnAdd.setVisibility(GONE);
        btnClose.setVisibility(GONE);
        btnUpload.setVisibility(GONE);
        btnFilter.setVisibility(GONE);
        btnCamera.setVisibility(GONE);
        btnSearch.setVisibility(GONE);
        btnSettings.setVisibility(GONE);
        txtTitle.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDrawerToggle) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START, true);
            } else {
                drawer.openDrawer(GravityCompat.START, true);
            }
        } else if (v == btnBack) {
            onBackPressed();
        } else if (v == btnDropDown) {
            UtilFunctions.showProfileChooseDialog(this, AppManager.profiles, this);
        }
    }

    private void removeFragment() {
        if (mFragmentStack.size() > 1) {
            mFragmentStack.pop();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(mFragmentStack.peek());
            updateUI(fragment, mFragmentStack.peek());
            transaction.show(fragment);
            transaction.commit();
        }
    }

    @Override
    public void onSuccess(ArrayList<Profile> profiles, boolean isLastPage, int page) {
        // Save profiles
        AppManager.profiles.clear();
        for (int i = 0; i < profiles.size(); i++) {
            AppManager.profiles.add(profiles.get(i));
        }
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.showMessageDialog(this, "Error", error);
    }

    @Override
    public void onContinue(Profile profile) {
        UtilFunctions.setCurrentProfile(this, profile);
        if (currentFragment.getClass() != MainFragment.class) {
            MainFragment mainFragment = MainFragment.newInstance();
            switchFragment(mainFragment, true, getResources().getString(R.string.dashboard_title));
        } else {
            MainFragment mainFragment = (MainFragment) currentFragment;
            mainFragment.getAllWallets();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        initHeader();
    }
}
