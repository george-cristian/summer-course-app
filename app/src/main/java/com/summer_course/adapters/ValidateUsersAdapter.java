package com.summer_course.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.summer_course.R;
import com.summer_course.database_classes.User;
import com.summer_course.utils.Constants;

import java.util.ArrayList;

/**
 * @author George Cristian
 *
 * Adapter used to display the users to be validate in the {@link com.summer_course.DashboardActivity}
 */
public class ValidateUsersAdapter extends RecyclerView.Adapter<ValidateUsersAdapter.UserViewHolder> {

    /**
     * The list of users to be validated
     */
    private ArrayList<User> users;

    public ValidateUsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ValidateUsersAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.validate_user_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        UserViewHolder viewHolder = new UserViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ValidateUsersAdapter.UserViewHolder holder, int position) {
        holder.bind(users.get(position), position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void removeUserAt(int position) {
        users.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, users.size());
    }

    /**
     * @author George Cristian
     *
     * The View which contains one user to be shown
     */
    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameTextView;
        private Button validateUserButton;

        public UserViewHolder(View itemView) {
            super(itemView);

            userNameTextView = itemView.findViewById(R.id.tv_validate_user_name);
            validateUserButton = itemView.findViewById(R.id.btn_validate_user);
        }

        public void bind(User user, int position) {
            userNameTextView.setText(user.getName());
            validateUserButton.setOnClickListener(new ValidateUserClickListener(user, position));
        }

    }

    /**
     * @author George Cristian
     *
     * Listener for the Validate button. It updated the database and removes the user from the list.
     */
    class ValidateUserClickListener implements View.OnClickListener {

        private User user;
        private int position;

        public ValidateUserClickListener(User user, int position) {
            this.user = user;
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(Constants.USERS_DATABASE).child(user.getUserID()).
                    child("validated").setValue(true, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    removeUserAt(position);
                }
            });

        }
    }

}
