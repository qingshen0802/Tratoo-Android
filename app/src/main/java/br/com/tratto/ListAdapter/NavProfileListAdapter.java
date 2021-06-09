package br.com.tratto.ListAdapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 9/29/17.
 */

public class NavProfileListAdapter extends BaseAdapter {

    private Context context;

    public NavProfileListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = vi.inflate(R.layout.list_item_create_new_profile, null);
        return convertView;
    }
}
