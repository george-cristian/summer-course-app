package com.summer_course.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.summer_course.R;
import com.summer_course.database_classes.User;
import com.summer_course.utils.BitmapUtils;
import com.summer_course.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author George Cristian
 *
 * Adapter for the gridview inside the fragment displayed in the
 * {@link com.summer_course.ParticipantsActivity}
 */
public class ParticipantsAdapter extends BaseAdapter {

    private ArrayList<User> personsList;
    private LayoutInflater inflater;

    public ParticipantsAdapter(LayoutInflater inflater, ArrayList<User> personsList) {
        this.personsList = personsList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return personsList.size();
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

        User currentUser = personsList.get(position);

        textView.setText(currentUser.getName());
        circleImageView.setImageBitmap(BitmapUtils.getBitmapFromString(currentUser.getProfilePicString()));

        convertView.setOnClickListener(new UserClickListener(currentUser, parent.getContext()));

        return convertView;
    }

    class UserClickListener implements View.OnClickListener {

        private User user;
        private Context context;

        public UserClickListener(User user, Context context) {
            this.user = user;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Constants.USER_PROFILE_ACTIVITY);
            intent.putExtra(Constants.USER_PROFILE, user);

            context.startActivity(intent);
        }
    }
}
