package br.com.tratto.Fragment.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.MenuListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static String TAG = "EditProfileFragment";
    private ListView listMenu;
    private FontTextView txtFullName, txtUsername;
    private CircleImageView profileImage;
    private MenuListAdapter menuListAdapter;

    private Profile currentProfile;
    private int[] menuTitles = {R.string.edit_profile_menu, R.string.edit_profile_location, R.string.edit_profile_document, R.string.edit_profile_bank, R.string.edit_profile_credit};
    private int[] menuSubTitles = {R.string.edit_profile_menu_sub, R.string.edit_profile_location_sub, R.string.edit_profile_document_sub, R.string.edit_profile_bank_sub, R.string.edit_profile_credit_sub};
    private int[] menuImages = {R.drawable.ic_user, R.drawable.ic_location, R.drawable.ic_assignment, R.drawable.ic_balance, R.drawable.ic_balance};

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        listMenu = (ListView) view.findViewById(R.id.listview_edit_profile_menu);
        txtFullName = (FontTextView) view.findViewById(R.id.text_edit_profile_full_name);
        txtUsername = (FontTextView) view.findViewById(R.id.text_edit_profile_username);
        profileImage = (CircleImageView) view.findViewById(R.id.btn_profile_edit);

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

    }
}
