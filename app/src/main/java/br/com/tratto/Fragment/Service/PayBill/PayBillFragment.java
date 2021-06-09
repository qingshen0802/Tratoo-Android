package br.com.tratto.Fragment.Service.PayBill;


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
public class PayBillFragment extends BaseFragment {


    public PayBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_bill, container, false);
    }

}
