package br.com.tratto.Fragment.DashBoard;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.tratto.APIManager.RapdTransactionManager;
import br.com.tratto.Activity.MainActivity;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.ManageWalletActionAdapter;
import br.com.tratto.ListAdapter.RapdTransactionAdapter;
import br.com.tratto.Model.RapdTransation;
import br.com.tratto.Model.Wallet;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletDetailFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, RapdTransactionManager.RapdTransactionCallback {

    private static String TAG = "WalletDetail Fragment";

    private GridView actionGridView;
    private ManageWalletActionAdapter actionAdapter;

    private ConstraintLayout walletInfoContainer;
    private FontButton btnPaySend, btnRequestCharge, btnActions, btnTransactions, currentButton;
    private FontTextView txtBalance;
    private ListView transactionListView;

    private Wallet wallet;
    private RapdTransactionManager rapdTransactionManager;
    private RapdTransactionAdapter rapdTransactionAdapter;

    private ArrayList<RapdTransation> rapdTransations = new ArrayList<>();

    public WalletDetailFragment() {
        // Required empty public constructor
    }

    public static WalletDetailFragment newInstance(Wallet wallet) {
        WalletDetailFragment fragment = new WalletDetailFragment();
        fragment.wallet = wallet;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_detail, container, false);

        actionGridView = view.findViewById(R.id.wallet_detail_grid_wallet_action);
        walletInfoContainer = view.findViewById(R.id.wallet_info_container);
        btnActions = view.findViewById(R.id.btn_wallet_detail_actions);
        btnTransactions = view.findViewById(R.id.btn_wallet_detail_transaction);
        btnPaySend = view.findViewById(R.id.btn_wallet_detail_pay_send);
        btnRequestCharge = view.findViewById(R.id.btn_wallet_detail_request_charge);
        txtBalance = view.findViewById(R.id.text_wallet_detail_balance);
        transactionListView = view.findViewById(R.id.wallet_detail_transaction_listview);

        initUI();
        return view;
    }

    private void initUI() {
        actionAdapter = new ManageWalletActionAdapter(getActivity());
        rapdTransactionAdapter = new RapdTransactionAdapter(getActivity(), rapdTransations);

        actionGridView.setAdapter(actionAdapter);
        transactionListView.setAdapter(rapdTransactionAdapter);
        actionGridView.setOnItemClickListener(this);
        transactionListView.setOnItemClickListener(this);
        btnPaySend.setOnClickListener(this);
        btnRequestCharge.setOnClickListener(this);
        btnActions.setOnClickListener(this);
        btnTransactions.setOnClickListener(this);

        txtBalance.setText(wallet.walletType.currency.shortName + " " + wallet.humanBalance);
        GradientDrawable drawable = (GradientDrawable) walletInfoContainer.getBackground();
        drawable.setColor(Color.parseColor("#" + wallet.walletType.color));
        btnActions.setSelected(true);
        currentButton = btnActions;

        getTransactions();
    }

    private void getTransactions() {
        UtilFunctions.showlLoadingDialog(getActivity());
        rapdTransactionManager = new RapdTransactionManager(getActivity(), this);
        rapdTransactionManager.getAllRapdTransactions(wallet.id);
    }

    private void showTransactionDetail(RapdTransation rapdTransation) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_transaction_receipt);

        FontTextView txtFullName = dialog.findViewById(R.id.text_receipt_dialog_full_name);
        FontTextView txtUsername = dialog.findViewById(R.id.text_receipt_dialog_username);
        FontTextView txtDate = dialog.findViewById(R.id.text_receipt_dialog_date);
        FontTextView txtAmount = dialog.findViewById(R.id.text_receipt_dialog_amount);
        FontTextView txtAuthNumber = dialog.findViewById(R.id.text_receipt_dialog_authorization);
        FontButton btnClose = dialog.findViewById(R.id.btn_receipt_dialog_close);
        CircleImageView profileImage = dialog.findViewById(R.id.receipt_dialog_profile_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (rapdTransation.amount >= 0) {
            txtFullName.setText(rapdTransation.fromProfile.fullName);
            txtUsername.setText("@" + rapdTransation.fromProfile.userName);
            txtAmount.setText("+" + rapdTransation.wallet.walletType.currency.shortName + " " + rapdTransation.humanAmount);
            if (!rapdTransation.fromProfile.photoUrl.equals("/images/original/missing.png")) {
                Picasso.with(getActivity()).load(rapdTransation.fromProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.user_placeholder);
            }
        } else {
            txtFullName.setText(rapdTransation.toProfile.fullName);
            txtUsername.setText("@" + rapdTransation.toProfile.userName);
            String amount = rapdTransation.humanAmount;
            amount = amount.substring(1);
            txtAmount.setText("-" + rapdTransation.wallet.walletType.currency.shortName + " " + amount);
            if (!rapdTransation.toProfile.photoUrl.equals("/images/original/missing.png")) {
                Picasso.with(getActivity()).load(rapdTransation.toProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.user_placeholder);
            }
        }
        txtDate.setText(UtilFunctions.convertDateToString(rapdTransation.createdAt));
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == transactionListView) {
            showTransactionDetail(rapdTransations.get(position));
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnActions || v == btnTransactions) {
            if (currentButton != v) {
                currentButton.setSelected(false);
                currentButton = (FontButton) v;
                currentButton.setSelected(true);
                if (currentButton == btnActions) {
                    actionGridView.setVisibility(View.VISIBLE);
                    transactionListView.setVisibility(View.INVISIBLE);
                } else {
                    actionGridView.setVisibility(View.INVISIBLE);
                    transactionListView.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (v == btnPaySend) {
                if (wallet.balance > 0) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.switchFragment(SendMoneyFragment.newInstance(wallet), false, getResources().getString(R.string.send_money_title));
                } else {
                    UtilFunctions.showMessageDialog(getActivity(), "Error", "Not enough money.");
                }
            } else {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchFragment(RequestMoneyFragment.newInstance(), false, getResources().getString(R.string.request_money_title));
            }
        }
    }

    @Override
    public void onCreate(RapdTransation rapdTransation) {

    }

    @Override
    public void onSuccess(ArrayList<RapdTransation> result) {
        UtilFunctions.dismissLoadingDialog();
        rapdTransations.clear();
        for (int i = 0; i < result.size(); i++) {
            rapdTransations.add(result.get(i));
        }
        rapdTransactionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }
}
