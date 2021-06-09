package br.com.tratto.Fragment;

import android.support.v4.app.Fragment;

import br.com.tratto.Activity.MainActivity;

/**
 * Created by lily on 9/27/17.
 */

public class BaseFragment extends Fragment {

    public void goBack() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onBackPressed();
    }
}
