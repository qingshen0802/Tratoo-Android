package br.com.tratto.ListAdapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Model.Wallet;
import br.com.tratto.R;

/**
 * Created by lily on 8/24/17.
 */

public class WalletListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Wallet> wallets;

    public WalletListAdapter(Context context, ArrayList<Wallet> wallets) {
        this.context = context;
        this.wallets = wallets;
    }

    @Override
    public int getCount() {
        return wallets.size();
    }

    @Override
    public Wallet getItem(int position) {
        return wallets.get(position);
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
            convertView = vi.inflate(R.layout.list_item_wallets, null);
            holder.txtCurrencyName = (FontTextView) convertView.findViewById(R.id.text_wallet_item_currency_name);
            holder.txtShortCurrencyName = (FontTextView) convertView.findViewById(R.id.text_wallet_item_short_currency_name);
            holder.txtBalance = (FontTextView) convertView.findViewById(R.id.text_wallet_item_balance);
            holder.walletTypeColor = (TextView) convertView.findViewById(R.id.text_wallet_item_wallet_type_color);
            holder.container = (ConstraintLayout) convertView.findViewById(R.id.container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtCurrencyName.setText(getItem(position).walletType.name);
        holder.txtShortCurrencyName.setText(getItem(position).walletType.currency.shortName);
        holder.txtBalance.setText(getItem(position).walletType.currency.shortName + " " + getItem(position).humanBalance);
        GradientDrawable drawable = (GradientDrawable) holder.walletTypeColor.getBackground();
        drawable.setColor(Color.parseColor("#" + getItem(position).walletType.color));
        GradientDrawable backDrawable = (GradientDrawable) holder.container.getBackground();
        backDrawable.setColor(Color.WHITE);
        holder.txtCurrencyName.setTextColor(Color.parseColor("#" + getItem(position).walletType.color));
        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtCurrencyName;
        private FontTextView txtBalance;
        private FontTextView txtShortCurrencyName;
        public TextView walletTypeColor;
        public ConstraintLayout container;
    }
}
