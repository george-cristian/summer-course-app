package com.summer_course.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.summer_course.R;

public class DashboardAdapter extends BaseAdapter{

    private static final int GRIDS_NUMBER = 4;

    private Context context;

    public DashboardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return GRIDS_NUMBER;
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

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        //convertView.setBackground(R.color.);

        ImageView gridItemImageView = convertView.findViewById(R.id.grid_item_image);
        TextView textView = convertView.findViewById(R.id.grid_item_text);

        textView.setText("Testy testy");
        gridItemImageView.setImageResource(R.drawable.calendar);

        return convertView;
    }
}
