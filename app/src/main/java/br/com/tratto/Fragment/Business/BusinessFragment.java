package br.com.tratto.Fragment.Business;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tratto.APIManager.BusinessManager;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.BusinessListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends BaseFragment implements AdapterView.OnItemClickListener, BusinessManager.BusinessCallback {

    private static String TAG = "BusinessFragment";
    private ListView businessListView;
    private ArrayList<Profile> businesses = new ArrayList<>();
    private BusinessListAdapter businessListAdapter;

    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        BusinessFragment fragment = new BusinessFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        businessListView = (ListView) view.findViewById(R.id.listview_business);
        businessListAdapter = new BusinessListAdapter(getActivity(), businesses);
        businessListView.setAdapter(businessListAdapter);
        businessListView.setOnItemClickListener(this);
        getAllBusiness();
        return view;
    }

    private void getAllBusiness() {
        UtilFunctions.showlLoadingDialog(getActivity());
        BusinessManager businessManager = new BusinessManager(getActivity(), this);
        businessManager.getAllBusiness();
    }

    @Override
    public void onSuccess(ArrayList<Profile> result) {
        UtilFunctions.dismissLoadingDialog();
        Log.d(TAG, String.valueOf(result.size()));
        businesses.clear();
        for (int i = 0; i < result.size(); i++) {
            businesses.add(result.get(i));
        }
        businessListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
