package br.com.tratto.Fragment.Service.WithdrawMoney;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.tratto.APIManager.BankAccountManager;
import br.com.tratto.APIManager.WithdrawManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.BankAccount;
import br.com.tratto.Model.Request.RequestWithdraw;
import br.com.tratto.Model.Response.NewWithdraw;
import br.com.tratto.Model.Wallet;
import br.com.tratto.Model.Withdraw;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawMoneyFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener, BankAccountManager.BankAccountCallback, WithdrawManager.WithdrawCallback {

    private static String TAG = "Withdraw Fragment";

    private FontTextView txtWithdrawAmount;
    private RadioButton rdoBank,rdoStore;
    private SeekBar seekAmount;
    private Spinner walletSelector, bankSelector, storeSelector;
    private FontButton btnContinue;

    private ArrayList<Wallet> wallets = new ArrayList<>();
    private double amount;

    private BankAccountManager bankAccountManager;
    private WithdrawManager withdrawManager;
    private ArrayList<BankAccount> bankAccounts;
    private ArrayList<String> bankNames;

    public WithdrawMoneyFragment() {
        // Required empty public constructor
    }

    public static WithdrawMoneyFragment newInstance() {
        WithdrawMoneyFragment fragment = new WithdrawMoneyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_money, container, false);

        txtWithdrawAmount = (FontTextView) view.findViewById(R.id.text_withdraw_amount);
        rdoBank = (RadioButton) view.findViewById(R.id.radio_withdraw_bank);
        rdoStore = (RadioButton) view.findViewById(R.id.radio_withdraw_store);
        seekAmount = (SeekBar) view.findViewById(R.id.seekbar_withdraw_amount);
        walletSelector = (Spinner) view.findViewById(R.id.spinner_withdraw_wallet);
        bankSelector = (Spinner) view.findViewById(R.id.spinner_withdraw_bank);
        storeSelector = (Spinner) view.findViewById(R.id.spinner_withdraw_store);
        btnContinue = (FontButton) view.findViewById(R.id.btn_withdraw_continue);

        rdoBank.setChecked(true);

        rdoStore.setOnClickListener(this);
        rdoBank.setOnClickListener(this);
        btnContinue.setOnClickListener(this);

        seekAmount.setOnSeekBarChangeListener(this);
        walletSelector.setOnItemSelectedListener(this);
        seekAmount.setMax(100);

        initWalletSelector();
        getBankAccounts();
        return view;

    }

    private void initWalletSelector () {
        wallets.clear();
        ArrayList<String> walletnames = new ArrayList<>();
        for (int i = 0; i < AppManager.wallets.size(); i++) {
            if (AppManager.wallets.get(i).walletType.withdrawable) {
                walletnames.add(AppManager.wallets.get(i).walletType.name);
                wallets.add(AppManager.wallets.get(i));
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, walletnames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        walletSelector.setAdapter(dataAdapter);
        walletSelector.setOnItemSelectedListener(this);
        btnContinue.setOnClickListener(this);
    }

    private void initAmountSlider() {
        seekAmount.setProgress(0);
        txtWithdrawAmount.setText(wallets.get(walletSelector.getSelectedItemPosition()).walletType.currency.shortName + "0.00");
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        amount = progress * wallets.get(walletSelector.getSelectedItemPosition()).balance / 100.0;
        amount = Math.round(amount * 100.0) / 100.0;
        txtWithdrawAmount.setText(wallets.get(walletSelector.getSelectedItemPosition()).walletType.currency.shortName + " " + String.valueOf(amount));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        if (v == rdoBank) {
            if (rdoBank.isChecked()) {
                rdoStore.setChecked(false);
            }
        } else if (v == rdoStore){
            if (rdoStore.isChecked()) {
                rdoBank.setChecked(false);
            }
        } else if (v == btnContinue){
            if (rdoBank.isChecked()) {
                UtilFunctions.showlLoadingDialog(getActivity());
                withdrawManager = new WithdrawManager(getActivity(), this);
                RequestWithdraw requestWithdraw = new RequestWithdraw();
                NewWithdraw newWithdraw = new NewWithdraw();
                newWithdraw.amount = amount;
                newWithdraw.currencyId = wallets.get(walletSelector.getSelectedItemPosition()).walletType.currencyId;
                newWithdraw.description = "";
                newWithdraw.status = "pending";
                newWithdraw.requestType = "bank_transfer";
                newWithdraw.profileId = UtilFunctions.getCurrentProfile(getActivity()).id;
                newWithdraw.userId = UtilFunctions.getCurrentUser(getActivity()).id;
                newWithdraw.walletId = wallets.get(walletSelector.getSelectedItemPosition()).id;
                requestWithdraw.newWithdraw = newWithdraw;
                withdrawManager.createWithdrawRequest(requestWithdraw);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        initAmountSlider();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onSuccess(ArrayList<BankAccount> result) {
        bankAccounts.clear();
        UtilFunctions.dismissLoadingDialog();
        bankAccounts = result;
        initBankSelector();
    }

    private void showWithdrawDone() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_withdraw_money_done);

        FontButton btnClose = (FontButton) dialog.findViewById(R.id.btn_dialog_withdraw_done_close);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goBack();
            }
        });
        dialog.show();
    }

    @Override
    public void onSuccess(Withdraw withdraw) {
        UtilFunctions.dismissLoadingDialog();
        showWithdrawDone();
    }
}
