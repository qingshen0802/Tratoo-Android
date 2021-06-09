package br.com.tratto.Fragment.DashBoard;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.tratto.APIManager.CurrencyManager;
import br.com.tratto.APIManager.ProfileManager;
import br.com.tratto.APIManager.TransactionRequestManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Currency;
import br.com.tratto.Model.NewTransactionRequest;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Request.RequestTransactionRequest;
import br.com.tratto.Model.TransactionRequest;
import br.com.tratto.R;
import br.com.tratto.Utils.CurrencyUtil;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.kolotnev.formattedittext.DecimalEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestMoneyFragment extends BaseFragment implements ProfileManager.ProfileManagerCallback,
        AdapterView.OnItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener,
        TransactionRequestManager.TransactionRequestCallback, CurrencyManager.CurrencyCallback {

    private static String TAG = "RequestMoneyFragment";

    private AutoCompleteTextView edtUserName;
    private Spinner spinnerWallet;
    private FontTextView txtCurrency;
    private FontEditText edtMessage;
    private DecimalEditText edtAmount;
    private FontButton btnContinue;
    private CircleImageView imgProfile;
    private TextInputLayout userLayout;
    private ImageView imageFlag;

    private ProfileManager profileManager;
    private ArrayList<Profile> allProfiles;
    private ArrayList<String> userNames;
    private ArrayList<Currency> allCurrencies;
    private Profile receiverProfile;

    private TransactionRequestManager transactionRequestManager;
    private CurrencyManager currencyManager;

    public RequestMoneyFragment() {
        // Required empty public constructor
    }

    public static RequestMoneyFragment newInstance() {
        RequestMoneyFragment fragment = new RequestMoneyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_money, container, false);
        imageFlag = view.findViewById(R.id.image_flag);
        edtUserName = view.findViewById(R.id.edit_request_money_username);
        edtAmount = view.findViewById(R.id.editText_request_money_amount);
        edtMessage = view.findViewById(R.id.editText_request_money_message);
        btnContinue = view.findViewById(R.id.btn_request_money_continue);
        txtCurrency = view.findViewById(R.id.text_request_money_wallet_currency);
        spinnerWallet = view.findViewById(R.id.spinner_request_money_wallet);
        imgProfile = view.findViewById(R.id.image_request_money_person);
        userLayout = view.findViewById(R.id.layout_user_name);
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                userLayout.setEnabled(false);
                userLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        initUI();

        return view;
    }

    private void initUI() {
        allProfiles = new ArrayList<>();
        allCurrencies = new ArrayList<>();

        profileManager = new ProfileManager(getActivity(), this);
        edtUserName.setThreshold(1);
        UtilFunctions.showlLoadingDialog(getActivity());
        profileManager.searchPeople(1);

        currencyManager = new CurrencyManager(getActivity(), this);
        currencyManager.getAllCurrencies();

        btnContinue.setOnClickListener(this);
        spinnerWallet.setOnItemSelectedListener(this);
    }

    private void initUserList() {
        userNames = new ArrayList<>();
        for (int i = 0; i < allProfiles.size(); i++) {
            userNames.add(allProfiles.get(i).userName);
        }
        if (getActivity() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, userNames);
            edtUserName.setAdapter(adapter);
            edtUserName.setOnItemClickListener(this);
        }
    }

    @Override
    public void onSuccess(ArrayList<Profile> profiles, boolean isLastPage, int page) {
        for (int i = 0; i < profiles.size(); i++) {
            allProfiles.add(profiles.get(i));
        }
        if (isLastPage) {
            UtilFunctions.dismissLoadingDialog();
            Log.d(TAG, String.valueOf(allProfiles.size()));
            initUserList();
        } else {
            int nextPage = page + 1;
            profileManager.searchPeople(nextPage);
        }
    }

    @Override
    public void onCreate(@Nullable TransactionRequest transactionRequest) {
        UtilFunctions.dismissLoadingDialog();
        if (transactionRequest != null) showTransactionDetail(transactionRequest);
    }

    @Override
    public void onGetAllCurrency(ArrayList<Currency> currencies) {
        UtilFunctions.dismissLoadingDialog();
        ArrayList<String> currencyNames = new ArrayList<>();
        allCurrencies.clear();
        for (int i = 0; i < currencies.size(); i++) {
            currencyNames.add(currencies.get(i).name);
            allCurrencies.add(currencies.get(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, currencyNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWallet.setAdapter(dataAdapter);
        imageFlag.setImageResource(CurrencyUtil.getFlag(allCurrencies.get(0)));
    }

    @Override
    public void onConvert(double convertedAmount) {

    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        txtCurrency.setText(allCurrencies.get(position).shortName);
        imageFlag.setImageResource(CurrencyUtil.getFlag(allCurrencies.get(position)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        txtCurrency.setText(allCurrencies.get(0).shortName);
    }

    @Override
    public void onClick(View v) {
        createTransactionRequest();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        updateReceiverUI(userNames.indexOf(parent.getItemAtPosition(position)));
    }

    private void updateReceiverUI(int position) {
        edtUserName.setText(allProfiles.get(position).fullName);
        String profilePhotoUrl = allProfiles.get(position).photoUrl;
        if (profilePhotoUrl == null || !profilePhotoUrl.equals("/images/original/missing.png")) {
            Picasso.with(getActivity()).load(profilePhotoUrl).placeholder(R.drawable.user_placeholder).into(imgProfile);
        } else {
            imgProfile.setImageResource(R.drawable.user_placeholder);
        }
        receiverProfile = allProfiles.get(position);
    }

    private void createTransactionRequest() {
        if (receiverProfile == null) {
            userLayout.setError(getString(R.string.no_user_inserted_message));
            userLayout.setErrorEnabled(true);
            return;
        }
        UtilFunctions.showlLoadingDialog(getActivity());
        RequestTransactionRequest requestTransactionRequest = new RequestTransactionRequest();
        NewTransactionRequest newTransactionRequest = new NewTransactionRequest();
        newTransactionRequest.amount = edtAmount.getValue().doubleValue();
        newTransactionRequest.description = edtMessage.getText().toString();
        newTransactionRequest.fromProfileId = UtilFunctions.getCurrentProfile(getActivity()).id;
        newTransactionRequest.toProfileId = receiverProfile.id;
        newTransactionRequest.currencyId = allCurrencies.get(spinnerWallet.getSelectedItemPosition()).id;
        newTransactionRequest.status = "pending";
        newTransactionRequest.userId = UtilFunctions.getCurrentUser(getActivity()).id;
        requestTransactionRequest.transaction = newTransactionRequest;

        transactionRequestManager = new TransactionRequestManager(getActivity(), this);
        transactionRequestManager.createTransactionRequest(requestTransactionRequest);
    }

    private void showTransactionDetail(@NonNull TransactionRequest transactionRequest) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_request_money_done);

        FontTextView txtFullName = dialog.findViewById(R.id.text_dialog_request_money_done_fullname);
        FontTextView txtUsername = dialog.findViewById(R.id.text_dialog_request_money_done_username);
        FontTextView txtAmount = dialog.findViewById(R.id.text_dialog_request_money_done_amount);
        FontTextView txtDescription = dialog.findViewById(R.id.text_dialog_request_money_description);
        FontButton btnClose = dialog.findViewById(R.id.btn_dialog_request_done_close);
        CircleImageView profileImage = dialog.findViewById(R.id.image_dialog_request_money_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goBack();
            }
        });
        txtFullName.setText(transactionRequest.toProfile.fullName);
        txtUsername.setText("@" + transactionRequest.toProfile.userName);
        txtAmount.setText(getCurrencyName(transactionRequest.currencyId) + " " + transactionRequest.humanAmount);
        if (!transactionRequest.toProfile.photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(getActivity()).load(transactionRequest.toProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.user_placeholder);
        }
        txtDescription.setText(edtMessage.getText().toString());
        dialog.show();
    }

    private String getCurrencyName(int id) {
        for (Currency currency : allCurrencies) {
            if (currency.id == id) return currency.shortName;
        }
        return "";
    }
}
