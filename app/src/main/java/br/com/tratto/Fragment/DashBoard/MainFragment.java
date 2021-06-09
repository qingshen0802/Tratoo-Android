package br.com.tratto.Fragment.DashBoard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tratto.APIManager.WalletManager;
import br.com.tratto.Activity.MainActivity;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.WalletListAdapter;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Wallet;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener, WalletManager.WalletCallback {

    private static String TAG = "MainFragment";

    private ListView walletListView;
    private Profile currentProfile;
    private ArrayList<Wallet> wallets = new ArrayList<>();
    private WalletListAdapter walletListAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        walletListView = (ListView) view.findViewById(R.id.listview_wallet);

        walletListAdapter = new WalletListAdapter(getActivity(), wallets);
        walletListView.setAdapter(walletListAdapter);
        walletListView.setOnItemClickListener(this);
        initUI();
        return view;
    }

    private void initUI() {
        getAllWallets();
    }

    public void getAllWallets() {
        currentProfile = UtilFunctions.getCurrentProfile(getActivity());
        UtilFunctions.showlLoadingDialog(getActivity());
        WalletManager walletManager = new WalletManager(getActivity(), this);
        walletManager.getAllWallets(currentProfile.id);
    }

    @Override
    public void onSuccess(ArrayList<Wallet> result) {
        UtilFunctions.dismissLoadingDialog();
        Log.d(TAG, String.valueOf(result.size()));
        wallets.clear();
        for (int i = 0; i < result.size(); i++) {
            wallets.add(result.get(i));
        }
        walletListAdapter.notifyDataSetChanged();
        AppManager.wallets = wallets;
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.switchFragment(WalletDetailFragment.newInstance(wallets.get(position)), false, wallets.get(position).walletType.name);
    }
}
