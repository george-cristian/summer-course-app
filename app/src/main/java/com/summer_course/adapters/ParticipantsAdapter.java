package com.summer_course.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.summer_course.R;
import com.summer_course.utils.BitmapUtils;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author George Cristian
 *
 * Adapter for the gridview inside the fragment displayed in the
 * {@link com.summer_course.ParticipantsActivity}
 */
public class ParticipantsAdapter extends BaseAdapter {

    private List<String> peopleNames;
    private List<String> peoplePics;
    private LayoutInflater inflater;

    public ParticipantsAdapter(LayoutInflater inflater, List<String> peopleNames, List<String> peoplePics) {
        this.peopleNames = peopleNames;
        this.peoplePics = peoplePics;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return peopleNames.size();
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
            convertView = inflater.inflate(R.layout.grid_item_participants, null);
        }

        CircleImageView circleImageView = convertView.findViewById(R.id.grid_item_participants_image);
        TextView textView = convertView.findViewById(R.id.grid_item_participants_text);

        textView.setText(peopleNames.get(position));
        circleImageView.setImageBitmap(BitmapUtils.getBitmapFromString(peoplePics.get(position)));

        return convertView;
    }
}
