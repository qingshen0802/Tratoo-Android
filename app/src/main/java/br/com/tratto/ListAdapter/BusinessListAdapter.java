package br.com.tratto.ListAdapter;

import android.content.Context;
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
 * Created by lily on 8/30/17.
 */

public class BusinessListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Profile> businesses;

    public BusinessListAdapter(Context context, ArrayList<Profile> businesses) {
        this.context = context;
        this.businesses = businesses;
    }

    @Override
    public int getCount() {
        return businesses.size();
    }

    @Override
    public Profile getItem(int position) {
        return businesses.get(position);
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
            convertView = vi.inflate(R.layout.list_item_business, null);
            holder.txtBusinessTitle = (FontTextView) convertView.findViewById(R.id.text_business_title);
            holder.txtBusinessUsername = (FontTextView) convertView.findViewById(R.id.text_business_username);
            holder.txtDistance = (FontTextView) convertView.findViewById(R.id.text_business_distance);
            holder.txtCreditAmount = (FontTextView) convertView.findViewById(R.id.text_business_credit_amount);
            holder.txtPercentage = (FontTextView) convertView.findViewById(R.id.text_business_percentage);
            holder.txtBusinessWallet = (FontTextView) convertView.findViewById(R.id.text_business_wallet);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.image_business_profile);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtBusinessTitle.setText(getItem(position).fullName);
        holder.txtBusinessUsername.setText("@" + getItem(position).userName);
        holder.txtCreditAmount.setText("R$ " + String.valueOf(getItem(position).creditAmount) + " crédito");
        if (!getItem(position).photoUrl.equals("/images/original/missing.png")) {
            Picasso.with(context).load(getItem(position).photoUrl).placeholder(R.drawable.user_placeholder).into(holder.profileImage);
        } else {
            holder.profileImage.setImageResource(R.drawable.user_placeholder);
        }
        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtBusinessTitle;
        private FontTextView txtBusinessUsername;
        private FontTextView txtDistance;
        private FontTextView txtCreditAmount;
        private FontTextView txtPercentage;
        private FontTextView txtBusinessWallet;
        private CircleImageView profileImage;
    }
}
