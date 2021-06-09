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
public class DeadlineFragment extends BaseFragment {



    public DeadlineFragment() {
        // Required empty public constructor
    }

    public static DeadlineFragment newInstance() {
        DeadlineFragment fragment = new DeadlineFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deadline, container, false);
        return view;
    }

}
