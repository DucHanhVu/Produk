package vn.viviu.produk.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import vn.viviu.produk.R;

public class HomeMenuAdapter extends BaseAdapter {
    private Context context;
    private int[] images;
    private int[] titles;
    private int[] colors;

    public HomeMenuAdapter(Context context, int[] images, int[] titles, int[] colors) {
        this.context = context;
        this.images = images;
        this.titles = titles;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_menu, viewGroup, false);
        }

        CardView cardMenu = view.findViewById(R.id.menu_card);
        ImageView imgMenu = view.findViewById(R.id.img_menu_item);
        TextView tvMenu = view.findViewById(R.id.tv_menu_item);

        cardMenu.setCardBackgroundColor(context.getResources().getColor(colors[position]));
        imgMenu.setImageResource(images[position]);
        tvMenu.setText(context.getString(titles[position]));
        return view;
    }

}
