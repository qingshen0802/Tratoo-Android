package br.com.tratto.Fragment.DashBoard;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.tratto.APIManager.CurrencyManager;
import br.com.tratto.APIManager.ProfileManager;
import br.com.tratto.APIManager.RapdTransactionManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Currency;
import br.com.tratto.Model.NewRapdTransaction;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.RapdTransation;
import br.com.tratto.Model.Request.RequestRapdTransactionRequest;
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
public class SendMoneyFragment extends BaseFragment implements ProfileManager.ProfileManagerCallback,
        AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener,
        RapdTransactionManager.RapdTransactionCallback, CurrencyManager.CurrencyCallback {

    private static String TAG = "SendMoneyFragment";

    private AutoCompleteTextView edtUserName;
    private Spinner spinnerWallet;
    private FontTextView txtCurrency;
    private FontEditText edtMessage;
    private DecimalEditText edtAmount;
    private FontButton btnContinue;
    private CircleImageView imgProfile;
    private TextInputLayout userLayout;
    private ImageView imageFlag;

    private Wallet wallet;
    private ProfileManager profileManager;
    private ArrayList<Profile> allProfiles;
    private ArrayList<String> userNames;
    private ArrayList<Currency> allCurrencies = new ArrayList<>();
    private Profile receiverProfile;
    private RapdTransactionManager rapdTransactionManager;

    public SendMoneyFragment() {
        // Required empty public constructor
    }

    public static SendMoneyFragment newInstance(@Nullable Wallet wallet) {
        SendMoneyFragment fragment = new SendMoneyFragment();
        fragment.wallet = wallet;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_money, container, false);
        imageFlag = view.findViewById(R.id.image_flag);
        edtUserName = view.findViewById(R.id.edit_send_money_username);
        edtAmount = view.findViewById(R.id.editText_send_money_amount);
        edtMessage = view.findViewById(R.id.editText_send_money_message);
        btnContinue = view.findViewById(R.id.btn_send_money_continue);
        txtCurrency = view.findViewById(R.id.text_send_money_wallet_currency);
        spinnerWallet = view.findViewById(R.id.spinner_send_money_wallet);
        imgProfile = view.findViewById(R.id.image_send_money_person);
        userLayout = view.findViewById(R.id.layout_user_name);
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                userLayout.setEnabled(false);
                userLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Wallet selected = getCurrent();
                Log.d(TAG, "onTextChanged: " + selected);
                if (selected.balance < edtAmount.getValue().doubleValue()) {
                    UtilFunctions.showMessageDialog(getContext(), "Error", "You don't have enough amount of money");
                }
            }
        });

        initUI();
        return view;
    }

    private void initUI() {
        allProfiles = new ArrayList<>();
        if (wallet != null) {
            txtCurrency.setText(wallet.walletType.currency.shortName);
        }
        profileManager = new ProfileManager(getActivity(), this);
        edtUserName.setThreshold(1);
        UtilFunctions.showlLoadingDialog(getActivity());
        profileManager.searchPeople(1);

        CurrencyManager currencyManager = new CurrencyManager(getActivity(), this);
        currencyManager.getAllCurrencies();

        spinnerWallet.setOnItemSelectedListener(this);
        btnContinue.setOnClickListener(this);
    }

    private void initUserList() {
        ArrayAdapter adapter = new UserFilterableAdapter(getActivity(), android.R.layout.simple_list_item_1, getUserNames(allProfiles));
        edtUserName.setAdapter(adapter);
        edtUserName.setOnItemClickListener(this);
    }

    private List<String> getUserNames(List<Profile> profiles) {
        userNames = new ArrayList<>();
        if (profiles == null || profiles.isEmpty()) return userNames;
        for (int i = 0; i < profiles.size(); i++) {
            userNames.add(profiles.get(i).fullName);
        }
        return userNames;
    }

    @Override
    public void onConvert(double convertedAmount) {

    }

    @Override
    public void onGetAllCurrency(ArrayList<Currency> currencies) {
        UtilFunctions.dismissLoadingDialog();
        ArrayList<String> currencyNames = new ArrayList<>();
        allCurrencies.clear();
        int pos = 0;
        for (int i = 0; i < currencies.size(); i++) {
            currencyNames.add(currencies.get(i).name);
            allCurrencies.add(currencies.get(i));
            if (currencies.get(i).id == wallet.walletType.currency.id) pos = i;
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, currencyNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWallet.setAdapter(dataAdapter);
        spinnerWallet.setSelection(pos);
        imageFlag.setImageResource(CurrencyUtil.getFlag(allCurrencies.get(pos)));
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
    public void onCreate(@Nullable RapdTransation rapdTransation) {
        UtilFunctions.dismissLoadingDialog();
        if (rapdTransation != null) showTransactionDetail(rapdTransation);
    }

    @Override
    public void onSuccess(ArrayList<RapdTransation> result) {
        UtilFunctions.dismissLoadingDialog();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getItemAtPosition(position);
        int index = findUser(item);
        Log.d(TAG, "onItemClick: " + item + ", index " + index);
        if (index != -1) updateReceiverUI(index);
    }

    private int findUser(String fullName) {
        for (int i = 0; i < allProfiles.size(); i++) {
            if (allProfiles.get(i).fullName.equals(fullName)) return i;
        }
        return -1;
    }

    private Wallet getCurrent() {
        return AppManager.wallets.get(spinnerWallet.getSelectedItemPosition());
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
        edtUserName.setEnabled(false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerWallet.getAdapter().getCount() > 1) {
            imageFlag.setImageResource(CurrencyUtil.getFlag(AppManager.wallets.get(position).walletType.currency));
        } else {
            imageFlag.setImageResource(CurrencyUtil.getFlag(wallet.walletType.currency));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        createTransaction();
    }

    private void createTransaction() {
        if (receiverProfile == null) {
            userLayout.setError(getString(R.string.no_user_inserted_message));
            userLayout.setErrorEnabled(true);
            return;
        }
        if (edtAmount.getValue().doubleValue() <= 0.0d) {
            UtilFunctions.showMessageDialog(getContext(), "Error", "Preencha o valor desejado");
            return;
        }
        UtilFunctions.showlLoadingDialog(getActivity());
        RequestRapdTransactionRequest requestRapdTransactionRequest = new RequestRapdTransactionRequest();
        NewRapdTransaction newRapdTransaction = new NewRapdTransaction();
        newRapdTransaction.amount = edtAmount.getValue().doubleValue();
        newRapdTransaction.description = edtMessage.getText().toString();
        newRapdTransaction.fromProfileId = UtilFunctions.getCurrentProfile(getActivity()).id;
        newRapdTransaction.toProfileId = receiverProfile.id;
        newRapdTransaction.fromWalletId = wallet.id;
        requestRapdTransactionRequest.rapdTransaction = newRapdTransaction;

        rapdTransactionManager = new RapdTransactionManager(getActivity(), this);
        rapdTransactionManager.createRapdTransaction(requestRapdTransactionRequest);
    }

    private void showTransactionDetail(@NonNull RapdTransation rapdTransation) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_money_done);

        FontTextView txtFullName = dialog.findViewById(R.id.text_dialog_send_money_done_fullname);
        FontTextView txtUsername = dialog.findViewById(R.id.text_dialog_send_money_done_username);
        FontTextView txtAmount = dialog.findViewById(R.id.text_dialog_send_money_done_amount);
        FontTextView txtDescription = dialog.findViewById(R.id.text_dialog_send_money_description);
        FontButton btnClose = dialog.findViewById(R.id.btn_dialog_send_done_close);
        CircleImageView profileImage = dialog.findViewById(R.id.image_dialog_send_money_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                goBack();
            }
        });
        txtFullName.setText(rapdTransation.toProfile.fullName);
        txtUsername.setText("@" + rapdTransation.toProfile.userName);
        txtAmount.setText(rapdTransation.wallet.walletType.currency.shortName + " " + rapdTransation.humanAmount);
        if (!rapdTransation.toProfile.photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(getActivity()).load(rapdTransation.toProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(profileImage);
        } else {
            profileImage.setImageResource(R.drawable.user_placeholder);
        }
        txtDescription.setText(edtMessage.getText().toString());
        dialog.show();
    }

    private class UserFilterableAdapter extends ArrayAdapter<String> {
        private List<Profile> filtered = new ArrayList<>();

        UserFilterableAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return nameFilter;
        }

        private Filter nameFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String prefix = constraint.toString().trim().toLowerCase();
                if (prefix.length() == 0) {
                    filtered = new ArrayList<>(allProfiles);
                    results.values = getUserNames(filtered);
                    results.count = filtered.size();
                } else {
                    int count = allProfiles.size();
                    filtered = new ArrayList<>(count);
                    for (int i = 0; i < count; i++) {
                        Profile profile = allProfiles.get(i);
                        String value = profile.fullName.toLowerCase();
                        String phone = "";
                        if (profile.phoneNumber != null) phone = profile.phoneNumber.toLowerCase();
                        if (value.contains(prefix) || phone.contains(prefix)) {
                            filtered.add(profile);
                        }
                    }
                    results.values = getUserNames(filtered);
                    results.count = filtered.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                List<String> profiles = (List<String>) filterResults.values;
                clear();
                int count = 0;
                if (profiles != null) count = profiles.size();
                for (int i = 0; i < count; i++) {
                    add(profiles.get(i));
                }

                if (count > 0) notifyDataSetChanged();
                else notifyDataSetInvalidated();
            }
        };
    }
}
