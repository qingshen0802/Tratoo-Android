package br.com.tratto.Fragment.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.MenuListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static String TAG = "ContactFragment";
    private ListView listMenu;
    private MenuListAdapter menuListAdapter;

    private Profile currentProfile;
    private int[] menuTitles = {R.string.contact_add_person, R.string.contact_people, R.string.contact_star};
    private int[] menuSubTitles = {R.string.contact_add_person_sub, R.string.contact_people_sub, R.string.contact_star_sub};
    private int[] menuImages = {R.drawable.ic_add_person, R.drawable.ic_people, R.drawable.ic_star};


    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        listMenu = (ListView) view.findViewById(R.id.listview_contact_menu);
        menuListAdapter = new MenuListAdapter(getActivity(), menuImages, menuTitles, menuSubTitles);
        listMenu.setAdapter(menuListAdapter);
        listMenu.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
