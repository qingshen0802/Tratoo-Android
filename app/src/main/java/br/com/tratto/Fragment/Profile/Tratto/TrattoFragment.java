package br.com.tratto.Fragment.Profile.Tratto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.tratto.Activity.MainActivity;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.MenuListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrattoFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static String TAG = "TrattoFragment";
    private ListView listMenu;
    private MenuListAdapter menuListAdapter;

    private Profile currentProfile;
    private int[] menuTitles = {R.string.tratto_help, R.string.tratto_about_us, R.string.tratto_contact_us};
    private int[] menuSubTitles = {R.string.tratto_help_sub, R.string.tratto_about_us_sub, R.string.tratto_contact_us_sub};
    private int[] menuImages = {R.drawable.ic_help, R.drawable.ic_brand, R.drawable.ic_contact};

    public TrattoFragment() {
        // Required empty public constructor
    }

    public static TrattoFragment newInstance() {
        TrattoFragment fragment = new TrattoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tratto, container, false);

        listMenu = (ListView) view.findViewById(R.id.listview_tratto_menu);
        menuListAdapter = new MenuListAdapter(getActivity(), menuImages, menuTitles, menuSubTitles);
        listMenu.setAdapter(menuListAdapter);
        listMenu.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (position) {
            case 0:
                mainActivity.switchFragment(HelpFragment.newInstance(), false, getResources().getString(R.string.tratto_help));
                break;
            case 1:
                mainActivity.switchFragment(AboutFragment.newInstance(), false, getResources().getString(R.string.tratto_about_us));
                break;
            case 2:
                mainActivity.switchFragment(ContactUsFragment.newInstance(), false, getResources().getString(R.string.tratto_contact_us));
                break;
        }
    }
}
