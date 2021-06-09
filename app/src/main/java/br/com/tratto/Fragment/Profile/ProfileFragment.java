package br.com.tratto.Fragment.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import br.com.tratto.Activity.MainActivity;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Fragment.Profile.Tratto.TrattoFragment;
import br.com.tratto.ListAdapter.MenuListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    private static String TAG = "ProfileFragment";
    private ListView listMenu;
    private FontTextView txtFullName, txtUsername;
    private CircleImageView profileImage;
    private MenuListAdapter menuListAdapter;

    private Profile currentProfile;
    private int[] menuTitles = {R.string.menu_profile, R.string.menu_contact, R.string.menu_setting, R.string.menu_tratto};
    private int[] menuSubTitles = {R.string.menu_profile_sub, R.string.menu_contact_sub, R.string.menu_setting_sub, R.string.menu_tratto_sub};
    private int[] menuImages = {R.drawable.ic_user, R.drawable.ic_people, R.drawable.ic_setting, R.drawable.ic_brand};

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        listMenu = view.findViewById(R.id.listview_profile_menu);
        txtFullName = view.findViewById(R.id.text_profile_full_name);
        txtUsername = view.findViewById(R.id.text_profile_username);
        profileImage = view.findViewById(R.id.btn_profile_menu);

        initUI();
        return view;
    }

    private void initUI() {
        currentProfile = UtilFunctions.getCurrentProfile(getActivity());
        if (!currentProfile.photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(getActivity()).load(currentProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(profileImage);
        }
        txtUsername.setText("@" + currentProfile.userName);
        txtFullName.setText(currentProfile.fullName);
        menuListAdapter = new MenuListAdapter(getActivity(), menuImages, menuTitles, menuSubTitles);
        listMenu.setAdapter(menuListAdapter);
        listMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (position == 0) {
            mainActivity.switchFragment(EditProfileFragment.newInstance(), false, getResources().getString(R.string.menu_profile));
        } else if (position == 1) {
            mainActivity.switchFragment(ContactFragment.newInstance(), false, getResources().getString(R.string.menu_contact));
        } else if (position == 2) {
            mainActivity.switchFragment(SettingsFragment.newInstance(), false, getResources().getString(R.string.menu_setting));
        } else if (position == 3) {
            mainActivity.switchFragment(TrattoFragment.newInstance(), false, getResources().getString(R.string.menu_tratto));
        }
    }
}
