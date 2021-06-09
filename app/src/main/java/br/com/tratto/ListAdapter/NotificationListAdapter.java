package br.com.tratto.ListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Model.Notification;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 8/30/17.
 */

public class NotificationListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationListAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Notification getItem(int position) {
        return notifications.get(position);
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
            convertView = vi.inflate(R.layout.list_item_notification, null);
            holder.txtTitle = (FontTextView) convertView.findViewById(R.id.item_notification_title);
            holder.txtDescription = (FontTextView) convertView.findViewById(R.id.item_notification_description);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.item_notification_profile_image);
            holder.dotView = (RelativeLayout) convertView.findViewById(R.id.dot_unread_flag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(getItem(position).title);
        holder.txtDescription.setText(getItem(position).description);
        String profilePhotoUrl = "/images/original/missing.png";
        if (getItem(position).type.equals("PaymentRequestNotification")) {
            profilePhotoUrl = getItem(position).toUserPhotoUrl;
        } else if (getItem(position).type.equals("MentionNotification")) {
            profilePhotoUrl = getItem(position).fromUserPhotoUrl;
        } else if (getItem(position).type.equals("MessageNotification")) {
            profilePhotoUrl = getItem(position).fromUserPhotoUrl;
        } else if (getItem(position).type.equals("PromotionNotification")) {
            profilePhotoUrl = getItem(position).fromUserPhotoUrl;
        } else if (getItem(position).type.equals("PaymentNotification")) {
            profilePhotoUrl = getItem(position).toUserPhotoUrl;
        } else if (getItem(position).type.equals("ReceiptNotification")) {
            profilePhotoUrl = getItem(position).fromUserPhotoUrl;
        } else if (getItem(position).type.equals("WarningNotification")) {
            profilePhotoUrl = getItem(position).fromUserPhotoUrl;
        }
        holder.profileImage.setBorderColor(Color.parseColor("#" + getItem(position).color));
        if (profilePhotoUrl == null || !profilePhotoUrl.equals("/images/original/missing.png")) {
            Picasso.with(context).load(profilePhotoUrl).placeholder(R.drawable.user_placeholder).into(holder.profileImage);
        } else {
            holder.profileImage.setImageResource(R.drawable.user_placeholder);
        }
        if (getItem(position).read) {
            holder.dotView.setVisibility(View.INVISIBLE);
        } else {
            holder.dotView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtTitle;
        private FontTextView txtDescription;
        private CircleImageView profileImage;
        private RelativeLayout dotView;
    }
}
