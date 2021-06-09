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
import br.com.tratto.Model.RapdTransation;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 9/6/17.
 */

public class RapdTransactionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RapdTransation> rapdTransations;

    public RapdTransactionAdapter(Context context, ArrayList<RapdTransation> rapdTransations) {
        this.context = context;
        this.rapdTransations = rapdTransations;
    }

    @Override
    public int getCount() {
        return rapdTransations.size();
    }

    @Override
    public RapdTransation getItem(int position) {
        return rapdTransations.get(position);
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
            convertView = vi.inflate(R.layout.list_item_rapd_transaction, null);
            holder.txtFullName = convertView.findViewById(R.id.text_rapd_item_full_name);
            holder.txtTimestamp = convertView.findViewById(R.id.text_rapd_item_timestamp);
            holder.txtAmount = convertView.findViewById(R.id.text_rapd_item_amount);
            holder.profileImage = convertView.findViewById(R.id.image_rapd_item_profile);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (getItem(position).amount >= 0) {
            holder.txtFullName.setText(getItem(position).fromProfile.fullName);
            holder.txtAmount.setText(getItem(position).wallet.walletType.currency.shortName + " " + getItem(position).humanAmount);
            holder.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.green));
            if (!getItem(position).fromProfile.photoUrl.equals("/images/original/missing.png")) {
                Picasso.with(context).load(getItem(position).fromProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.user_placeholder);
            }
        } else {
            holder.txtFullName.setText(getItem(position).toProfile.fullName);
            String amount = getItem(position).humanAmount;
            amount = amount.substring(1);
            holder.txtAmount.setText("-" + getItem(position).wallet.walletType.currency.shortName + " " + amount);
            holder.txtAmount.setTextColor(ContextCompat.getColor(context, R.color.light_pink));
            if (!getItem(position).toProfile.photoUrl.equals("/images/original/missing.png")) {
                Picasso.with(context).load(getItem(position).toProfile.photoUrl).placeholder(R.drawable.user_placeholder).into(holder.profileImage);
            } else {
                holder.profileImage.setImageResource(R.drawable.user_placeholder);
            }
        }
        holder.txtTimestamp.setText(getItem(position).humanTimeStamp);

        return convertView;
    }

    private static class ViewHolder {
        private CircleImageView profileImage;
        private FontTextView txtFullName;
        private FontTextView txtTimestamp;
        private FontTextView txtAmount;
    }
}
