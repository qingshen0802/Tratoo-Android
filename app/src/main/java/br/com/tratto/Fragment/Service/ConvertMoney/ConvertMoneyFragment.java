package br.com.tratto.Fragment.Service.ConvertMoney;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.tratto.APIManager.ConvertManager;
import br.com.tratto.APIManager.CurrencyManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Convert;
import br.com.tratto.Model.Currency;
import br.com.tratto.Model.NewConvert;
import br.com.tratto.Model.Request.RequestConversion;
import br.com.tratto.Model.Wallet;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.CurrencyUtil;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.kolotnev.formattedittext.DecimalEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConvertMoneyFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, CurrencyManager.CurrencyCallback, ConvertManager.ConvertCallback {

    private static String TAG = "Convert Fragment";
    private Spinner sourceWalletSelector, targetWalletSelector;
    private FontTextView txtSourceCurrency, txtTargetCurrency, txtConvertHelpValue;
    private DecimalEditText edtSourceAmount, edtTargetAmount;
    private FontButton btnContinue;
    private CircleImageView fromFlagView;
    private CircleImageView toFlagView;

    private ArrayList<Wallet> wallets = new ArrayList<>();
    private CurrencyManager currencyManager;
    private ConvertManager convertManager;

    private double currencyRatio;

    public ConvertMoneyFragment() {
        // Required empty public constructor
    }

    public static ConvertMoneyFragment newInstance() {
        ConvertMoneyFragment fragment = new ConvertMoneyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_convert_money, container, false);

        fromFlagView = view.findViewById(R.id.image_convert_money_source_flag);
        toFlagView = view.findViewById(R.id.image_convert_money_target_flag);

        sourceWalletSelector = view.findViewById(R.id.spinner_convert_money_source_wallet);
        targetWalletSelector = view.findViewById(R.id.spinner_convert_money_target_wallet_selector);
        txtSourceCurrency = view.findViewById(R.id.text_convert_money_source_currency_name);
        txtTargetCurrency = view.findViewById(R.id.text_convert_money_target_currency_name);
        txtConvertHelpValue = view.findViewById(R.id.text_convert_money_help_value);
        edtSourceAmount = view.findViewById(R.id.edit_convert_money_source_amount);
        edtTargetAmount = view.findViewById(R.id.edit_convert_money_tareget_amount);
        btnContinue = view.findViewById(R.id.btn_convert_money_continue);

        btnContinue.setOnClickListener(this);
        edtSourceAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateConversionUI();
            }
        });
        initWalletSelector();
        currencyManager = new CurrencyManager(getActivity(), this);

        return view;
    }

    private void initWalletSelector () {
        wallets.clear();
        ArrayList<String> walletnames = new ArrayList<>();
        for (int i = 0; i < AppManager.wallets.size(); i++) {
            if (AppManager.wallets.get(i).walletType.conversionable) {
                wallets.add(AppManager.wallets.get(i));
                walletnames.add(AppManager.wallets.get(i).walletType.name);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, walletnames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceWalletSelector.setAdapter(dataAdapter);
        targetWalletSelector.setAdapter(dataAdapter);
        sourceWalletSelector.setOnItemSelectedListener(this);
        targetWalletSelector.setOnItemSelectedListener(this);
    }

    private void updateConversionUI() {
        double toAmount = currencyRatio * edtSourceAmount.getValue().doubleValue();
        edtTargetAmount.setText(String.valueOf(Math.round(toAmount * 100.0)/ 100.0));
    }

    private void updateRatio() {
        currencyManager.convertCurrency(wallets.get(sourceWalletSelector.getSelectedItemPosition()).walletType.currencyId, wallets.get(targetWalletSelector.getSelectedItemPosition()).walletType.currencyId, 100);
    }

    @Override
    public void onClick(View v) {

        if (wallets.get(sourceWalletSelector.getSelectedItemPosition()).walletType.currencyId == wallets.get(targetWalletSelector.getSelectedItemPosition()).walletType.currencyId) {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Please choose different wallets.");
        } else if (wallets.get(sourceWalletSelector.getSelectedItemPosition()).balance < edtSourceAmount.getValue().doubleValue()) {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Not enough money.");
        } else if (edtSourceAmount.getValue().doubleValue() <= 0.0) {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Preencha o valor desejado.");
        } else {
            UtilFunctions.showlLoadingDialog(getActivity());
            convertManager = new ConvertManager(getActivity(), this);
            RequestConversion conversion = new RequestConversion();
            NewConvert convert = new NewConvert();
            convert.profileId = UtilFunctions.getCurrentProfile(getActivity()).id;
            convert.fromCurrencyId = wallets.get(sourceWalletSelector.getSelectedItemPosition()).walletType.currencyId;
            convert.toCurrencyId = wallets.get(targetWalletSelector.getSelectedItemPosition()).walletType.currencyId;
            convert.fromAmount = edtSourceAmount.getValue().doubleValue();
            convert.toAmount = edtTargetAmount.getValue().doubleValue();
            conversion.convert = convert;
            convertManager.createConversionRequest(conversion);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, view.toString());
        if (parent == sourceWalletSelector) {
            txtSourceCurrency.setText(wallets.get(position).walletType.currency.shortName);
            fromFlagView.setImageResource(CurrencyUtil.getFlag(wallets.get(position).walletType.currency));
            updateRatio();
        } else if (parent == targetWalletSelector){
            txtTargetCurrency.setText(wallets.get(position).walletType.currency.shortName);
            toFlagView.setImageResource(CurrencyUtil.getFlag(wallets.get(position).walletType.currency));
            updateRatio();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onGetAllCurrency(ArrayList<Currency> currencies) {

    }

    @Override
    public void onConvert(double convertedAmount) {
        currencyRatio = convertedAmount/100;
        txtConvertHelpValue.setText("1.00" + wallets.get(sourceWalletSelector.getSelectedItemPosition()).walletType.currency.shortName + " = " + wallets.get(targetWalletSelector.getSelectedItemPosition()).walletType.currency.shortName + String.valueOf(Math.round(currencyRatio * 100.0)/100.0) + ", 1.00" +
                wallets.get(targetWalletSelector.getSelectedItemPosition()).walletType.currency.shortName + " = " + wallets.get(sourceWalletSelector.getSelectedItemPosition()).walletType.currency.shortName + String.valueOf(Math.round(1/currencyRatio * 100.0)/100.0));
        updateConversionUI();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onSuccess(Convert convert) {
        UtilFunctions.dismissLoadingDialog();
        showConvertDone();
    }

    private void showConvertDone() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_convert_money_done);

        FontButton btnClose = dialog.findViewById(R.id.btn_dialog_convert_done_close);
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
}
