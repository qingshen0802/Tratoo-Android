package br.com.tratto.Fragment.Service.LoadMoney;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.tratto.Activity.MainActivity;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Wallet;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import ru.kolotnev.formattedittext.DecimalEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadMoneyFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static String TAG = "LoadMoney Fragment";

    private FontButton btnContinue, btnCheckRate;
    private DecimalEditText edtAmount;
    private Spinner walletSelector, methodSelector;
    private FontTextView txtCurrencyName;
    private ConstraintLayout infoContainer;

    private Wallet wallet;


    public LoadMoneyFragment() {
        // Required empty public constructor
    }

    public static LoadMoneyFragment newInstance(@Nullable Wallet wallet) {
        LoadMoneyFragment fragment = new LoadMoneyFragment();
        fragment.wallet = wallet;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_load_money, container, false);
        btnContinue = (FontButton) view.findViewById(R.id.load_money_btn_continue);
        btnCheckRate = (FontButton) view.findViewById(R.id.load_money_btn_check);
        edtAmount = (DecimalEditText) view.findViewById(R.id.edit_load_money_amount);
        txtCurrencyName = (FontTextView) view.findViewById(R.id.text_load_money_currency_name);
        methodSelector = (Spinner) view.findViewById(R.id.spinner_load_money_transfer_method);
        walletSelector = (Spinner) view.findViewById(R.id.spinner_load_money_wallet);
        infoContainer = (ConstraintLayout) view.findViewById(R.id.container_00001);
        GradientDrawable drawable = (GradientDrawable) infoContainer.getBackground();
        drawable.setColor(Color.WHITE);

        initUI();
        return view;
    }

    private void initUI () {
        initWalletSelector();
    }

    private void initWalletSelector () {
        ArrayList<String> walletnames = new ArrayList<>();
        for (int i = 0; i < AppManager.wallets.size(); i++) {
            walletnames.add(AppManager.wallets.get(i).walletType.name);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, walletnames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        walletSelector.setAdapter(dataAdapter);
        walletSelector.setOnItemSelectedListener(this);
        btnContinue.setOnClickListener(this);
        btnCheckRate.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        txtCurrencyName.setText(AppManager.wallets.get(position).walletType.currency.shortName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (v == btnContinue) {
            if (methodSelector.getSelectedItemPosition() == 1) {
                mainActivity.switchFragment(BankTransferFragment.newInstance(), false, getResources().getString(R.string.bank_transfer_title));
            } else if (methodSelector.getSelectedItemPosition() == 2) {
                mainActivity.switchFragment(BankSlipFragment.newInstance(), false, getResources().getString(R.string.bank_slip_title));
            }
        } else {
            mainActivity.switchFragment(DeadlineFragment.newInstance(), false, getResources().getString(R.string.rate_deadline_title));
        }
    }
}
