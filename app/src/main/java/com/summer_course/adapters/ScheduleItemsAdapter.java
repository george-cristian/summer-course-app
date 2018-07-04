package com.summer_course.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.summer_course.R;
import com.summer_course.database_classes.ScheduleEvent;
import com.summer_course.utils.Constants;

import java.util.ArrayList;

/**
 * @author George Cristian
 *
 * Adapter class for the {@link RecyclerView} used in the {@link com.summer_course.ScheduleActivity}.
 */
public class ScheduleItemsAdapter extends RecyclerView.Adapter<ScheduleItemsAdapter.EventViewHolder>{

    /**
     * The list of events for one single day
     */
    private ArrayList<ScheduleEvent> eventsList;

    public ScheduleItemsAdapter(ArrayList<ScheduleEvent> eventsList) {
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.event_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        EventViewHolder viewHolder = new EventViewHolder(view, context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(eventsList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventTextView;
        private View eventView;
        private Context context;

        public EventViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;
            this.eventView = itemView;
            this.eventTextView = itemView.findViewById(R.id.event_item_text);
        }

        public void bind(ScheduleEvent event) {
            String textString = event.getHours() + "        " + event.getTitle();
            eventTextView.setText(textString);

            this.eventView.setOnClickListener(new EventClickListener(event, context));
        }

    }

    class EventClickListener implements View.OnClickListener {

        private ScheduleEvent event;
        private Context context;

        public EventClickListener(ScheduleEvent event, Context context) {
            this.event = event;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent eventProfileIntent = new Intent(Constants.EVENT_PROFILE_ACTIVITY);

            eventProfileIntent.putExtra(Constants.SCHEDULE_EVENT, this.event);

            context.startActivity(eventProfileIntent);
        }
    }
}
