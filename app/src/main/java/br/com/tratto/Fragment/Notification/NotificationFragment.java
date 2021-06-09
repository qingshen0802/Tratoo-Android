package br.com.tratto.Fragment.Notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.tratto.APIManager.NotificationManager;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.ListAdapter.NotificationListAdapter;
import br.com.tratto.Model.Notification;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements AdapterView.OnItemClickListener, NotificationManager.NotificationCallback {

    private static String TAG = "NotificationFragment";
    private ListView notificationListView;
    private NotificationListAdapter notificationListAdapter;
    private ArrayList<Notification> notifications = new ArrayList<>();

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationListView = (ListView) view.findViewById(R.id.listview_notification);
        notificationListAdapter = new NotificationListAdapter(getActivity(), notifications);
        notificationListView.setAdapter(notificationListAdapter);
        notificationListView.setOnItemClickListener(this);
        getNotifications();

        return view;
    }

    private void getNotifications() {
        UtilFunctions.showlLoadingDialog(getActivity());
        NotificationManager notificationManager = new NotificationManager(getActivity(), this);
        notificationManager.getAllNotifications();
    }

    @Override
    public void onSuccess(ArrayList<Notification> result) {
        UtilFunctions.dismissLoadingDialog();
        Log.d(TAG, String.valueOf(notifications.size()));
        notifications.clear();
        for (int i = 0; i < result.size(); i++) {
            notifications.add(result.get(i));
        }
        notificationListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
