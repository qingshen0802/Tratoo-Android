package br.com.tratto.Fragment.Service.LoadMoney;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankSlipFragment extends BaseFragment {


    public BankSlipFragment() {
        // Required empty public constructor
    }

    public static BankSlipFragment newInstance() {
        BankSlipFragment fragment = new BankSlipFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_slip, container, false);
        return view;
    }

}
