package br.com.tratto.ListAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lily on 8/30/17.
 */

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private int[] menuImages;
    private int[] menuTitles;
    private int[] menuSubTitles;

    public MenuListAdapter(Context context, int[] menuImages, int[] menuTitles, int[] menuSubTitles) {
        this.context = context;
        this.menuImages = menuImages;
        this.menuTitles = menuTitles;
        this.menuSubTitles = menuSubTitles;
    }

    @Override
    public int getCount() {
        return menuImages.length;
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
        ViewHolder holder;
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = vi.inflate(R.layout.list_item_menu, null);
            holder.txtMenuTitle = (FontTextView) convertView.findViewById(R.id.text_menu_item_title);
            holder.txtMenuSubTitle = (FontTextView) convertView.findViewById(R.id.text_menu_item_subtitle);
            holder.menuIcon = (ImageView) convertView.findViewById(R.id.image_menu_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtMenuTitle.setText(menuTitles[position]);
        holder.txtMenuSubTitle.setText(menuSubTitles[position]);
        holder.menuIcon.setImageResource(menuImages[position]);

        return convertView;
    }

    private static class ViewHolder {
        private FontTextView txtMenuTitle;
        private FontTextView txtMenuSubTitle;
        private ImageView menuIcon;
    }
}
