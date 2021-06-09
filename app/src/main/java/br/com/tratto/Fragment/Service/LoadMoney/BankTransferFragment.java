package br.com.tratto.Fragment.Service.LoadMoney;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.tratto.APIManager.BankAccountManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.BankAccount;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankTransferFragment extends BaseFragment implements BankAccountManager.BankAccountCallback, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static String TAG = "BankTransferFragment";

    private FontTextView txtHeader, txtAgValue, txtCCValue, txtIdentificadorValue, txtCNPJValue;
    private FontButton btnContinue, btnAttachement;
    private Spinner bankSelector;

    private RelativeLayout infoContainer;

    private BankAccountManager bankAccountManager;
    private ArrayList<BankAccount> bankAccounts;
    private ArrayList<String> bankNames;

    public BankTransferFragment() {
        // Required empty public constructor
    }

    public static BankTransferFragment newInstance() {
        BankTransferFragment fragment = new BankTransferFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_transfer, container, false);

        txtHeader = (FontTextView) view.findViewById(R.id.text_bank_transfer_header);
        txtAgValue = (FontTextView) view.findViewById(R.id.text_bank_transfer_ag_value);
        txtCCValue = (FontTextView) view.findViewById(R.id.text_bank_transfer_cc_value);
        txtIdentificadorValue = (FontTextView) view.findViewById(R.id.text_bank_transfer_identifier_value);
        txtCNPJValue = (FontTextView) view.findViewById(R.id.text_bank_transfer_cnpj_value);
        btnContinue = (FontButton) view.findViewById(R.id.btn_bank_transfer_continue);
        btnAttachement = (FontButton) view.findViewById(R.id.btn_bank_transfer_add_attachment);
        bankSelector = (Spinner) view.findViewById(R.id.spinner_bank_transfer_bank_selector);

        btnContinue.setOnClickListener(this);
        btnAttachement.setOnClickListener(this);

        infoContainer = (RelativeLayout) view.findViewById(R.id.bank_transfer_bank_info_container);
        GradientDrawable drawable = (GradientDrawable) infoContainer.getBackground();
        drawable.setColor(Color.parseColor("#EFF1F4"));

        getBankAccounts();

        return view;
    }

    private void getBankAccounts() {
        bankAccounts = new ArrayList<>();
        UtilFunctions.showlLoadingDialog(getActivity());
        bankAccountManager = new BankAccountManager(getActivity(), this);
        bankAccountManager.getAllBankAccounts();
    }

    private void initBankSelector() {
        bankNames = new ArrayList<>();
        for (int i = 0; i < bankAccounts.size(); i++) {
            bankNames.add(bankAccounts.get(i).bankName);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, bankNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSelector.setAdapter(dataAdapter);
        bankSelector.setOnItemSelectedListener(this);
    }

    @Override
    public void onSuccess(ArrayList<BankAccount> result) {
        bankAccounts.clear();
        UtilFunctions.dismissLoadingDialog();
        bankAccounts = result;
        initBankSelector();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == btnContinue) {

        } else {

        }
    }
}
