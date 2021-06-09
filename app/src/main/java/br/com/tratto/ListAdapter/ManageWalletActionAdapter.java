package br.com.tratto.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.R;

/**
 * Created by lily on 8/30/17.
 */

public class ManageWalletActionAdapter extends BaseAdapter {

    private Context context;
    private int[] actionImages = {R.drawable.manage_load, R.drawable.manage_redeem, R.drawable.manage_convert, R.drawable.manage_report, R.drawable.manage_card, R.drawable.manage_invoice};
    private int[] actionTexts = {R.string.manage_load, R.string.manage_redeem, R.string.manage_convert, R.string.manage_report, R.string.manage_card, R.string.manage_invoice};

    public ManageWalletActionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return actionImages.length;
    }

    @Override
    public Object getItem(int position) {
        return actionImages[position];
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
            convertView = vi.inflate(R.layout.grid_item_manage_wallet_action, null);
            holder.txtActionName = (FontTextView) convertView.findViewById(R.id.text_manage_wallet_action);
            holder.imgAction = (ImageView) convertView.findViewById(R.id.image_manage_wallet_action);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtActionName.setText(actionTexts[position]);
        holder.imgAction.setImageResource(actionImages[position]);

        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtActionName;
        private ImageView imgAction;
    }
}
