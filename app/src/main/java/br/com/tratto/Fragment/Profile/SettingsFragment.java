package br.com.tratto.Fragment.Profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import br.com.tratto.Activity.MainActivity;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static String TAG = "TrattoFragment";

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        RelativeLayout btnDiscount = view.findViewById(R.id.btn_settings_discount);
        RelativeLayout btnCoupon = view.findViewById(R.id.btn_settings_coupon);
        RelativeLayout btnPassword = view.findViewById(R.id.btn_settings_password);
        Switch switchTouchId = view.findViewById(R.id.switch_touch_id);
        Switch switchPush = view.findViewById(R.id.switch_push_notification);
        Switch switchCashStore = view.findViewById(R.id.switch_cash_store);

        btnDiscount.setOnClickListener(this);
        btnCoupon.setOnClickListener(this);
        btnPassword.setOnClickListener(this);

        switchCashStore.setOnCheckedChangeListener(this);
        switchPush.setOnCheckedChangeListener(this);
        switchTouchId.setOnCheckedChangeListener(this);

        View cashStoreView = view.findViewById(R.id.cash_store_view);
        View discountView = view.findViewById(R.id.discount_view);

        Profile currentProfile = UtilFunctions.getCurrentProfile(getActivity());

        if (currentProfile.type.equalsIgnoreCase("person")) {
            cashStoreView.setVisibility(View.GONE);
            discountView.setVisibility(View.GONE);
        } else {
            cashStoreView.setVisibility(View.VISIBLE);
            discountView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.btn_settings_password:
                mainActivity.switchFragment(ChangePasswordFragment.newInstance(), false, getString(R.string.settings_password));
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
