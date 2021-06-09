package br.com.tratto.ListAdapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Model.Profile;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 9/29/17.
 */

public class ProfileListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Profile> profiles;

    public ProfileListAdapter(Context context, ArrayList<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Profile getItem(int position) {
        return profiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = vi.inflate(R.layout.list_item_profile, null);
            holder.txtName = (FontTextView) convertView.findViewById(R.id.text_choose_profile_name);
            holder.txtType = (FontTextView) convertView.findViewById(R.id.text_choose_profile_role);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.image_choose_profile_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(getItem(position).fullName);
        if (getItem(position).type.equals("Person")) {
            holder.txtType.setText(context.getResources().getString(R.string.dialog_profile_choose_personal));
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.dark_blue));
        } else {
            holder.txtType.setText(context.getResources().getString(R.string.dialog_profile_choose_company));
            holder.txtName.setTextColor(ContextCompat.getColor(context, R.color.dark_pink));
        }
        if (!getItem(position).photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(context).load(getItem(position).photoUrl).placeholder(R.drawable.user_placeholder).into(holder.profileImage);
        } else {
            holder.profileImage.setImageResource(R.drawable.user_placeholder);
        }
        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtName;
        private FontTextView txtType;
        private CircleImageView profileImage;
    }
}
