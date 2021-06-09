package br.com.tratto.Fragment.Service;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import br.com.tratto.Activity.MainActivity;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Fragment.Service.ConvertMoney.ConvertMoneyFragment;
import br.com.tratto.Fragment.Service.LoadMoney.LoadMoneyFragment;
import br.com.tratto.Fragment.Service.WithdrawMoney.WithdrawMoneyFragment;
import br.com.tratto.ListAdapter.ManageWalletActionAdapter;
import br.com.tratto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageWalletFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    private GridView actionGridView;
    private ManageWalletActionAdapter actionAdapter;

    public ManageWalletFragment() {
        // Required empty public constructor
    }

    public static ManageWalletFragment newInstance() {
        ManageWalletFragment fragment = new ManageWalletFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        actionGridView = (GridView) view.findViewById(R.id.grid_manage_action);
        actionAdapter = new ManageWalletActionAdapter(getActivity());
        actionGridView.setAdapter(actionAdapter);

        actionGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (position) {
            case 0:
                mainActivity.switchFragment(LoadMoneyFragment.newInstance(null), false, getResources().getString(R.string.load_money_title));
                break;
            case 1:
                mainActivity.switchFragment(WithdrawMoneyFragment.newInstance(), false, getResources().getString(R.string.withdraw_money_title));
                break;
            case 2:
                mainActivity.switchFragment(ConvertMoneyFragment.newInstance(), false, getResources().getString(R.string.convert_money_title));
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
