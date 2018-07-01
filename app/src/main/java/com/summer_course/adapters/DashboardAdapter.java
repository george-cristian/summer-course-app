package com.summer_course.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.summer_course.R;
import com.summer_course.utils.Constants;

public class DashboardAdapter extends BaseAdapter{

    private static final int GRIDS_NUMBER = 4;

    private Context context;

    private String[] optionsTexts = {"Schedule & Events", "Participants", "Chats", "Survival Guide"};
    private int[] optionsImages =
            {R.drawable.calendar, R.drawable.participants, R.drawable.chat, R.drawable.survival_guide};

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

        ImageView gridItemImageView = convertView.findViewById(R.id.grid_item_image);
        TextView textView = convertView.findViewById(R.id.grid_item_text);

        textView.setText(optionsTexts[position]);
        gridItemImageView.setImageResource(optionsImages[position]);

        convertView.setOnClickListener(getClickListenerForPosition(position));

        return convertView;
    }

    private View.OnClickListener getClickListenerForPosition(final int position) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        startActivity(Constants.PARTICIPANTS_ACTIVITY);
                        break;
                    case 1:
                        startActivity(Constants.PARTICIPANTS_ACTIVITY);
                        break;
                    case 2:
                        startActivity(Constants.PARTICIPANTS_ACTIVITY);
                        break;
                    case 3:
                        startActivity(Constants.PARTICIPANTS_ACTIVITY);
                        break;
                    default:
                        startActivity(Constants.PARTICIPANTS_ACTIVITY);
                        break;
                }
            }
        };

        return clickListener;

    }

    private void startActivity(String activityString) {
        Intent changeActivityIntent = new Intent(activityString);
        context.startActivity(changeActivityIntent);
    }
}
