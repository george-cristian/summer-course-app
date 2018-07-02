package com.summer_course.database_classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.summer_course.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author George Cristian
 *
 * Class that holds the representation of an event exactly as in the database.
 * This class implements {@link Parcelable} because it is sent as an argument to a fragment.
 */
public class ScheduleEvent implements Parcelable {

    private String title;
    private String description;

    private long startTimestamp;
    private long stopTimestamp;

    private int type;

    private String eventID;

    public ScheduleEvent(String eventID, String title, String description, Long startTimestamp,
                         Long stopTimestamp, int type) {
        this.eventID = eventID;

        this.title = title;
        this.description = description;

        this.startTimestamp = startTimestamp;
        this.stopTimestamp = stopTimestamp;

        this.type = type;
    }

    public ScheduleEvent(Parcel parcel) {
        this.eventID = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.startTimestamp = parcel.readLong();
        this.stopTimestamp = parcel.readLong();
        this.type = parcel.readInt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return DateUtils.getDateFromTimestamp(startTimestamp);
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getStopTime() {
        return DateUtils.getDateFromTimestamp(stopTimestamp);
    }

    public void setStopTimestamp(long stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Method that creates a string which is the concatenation of the starting hour and ending hour
     * of the event.
     *
     * @return Concatenation of thr starting hour and ending hour of the event
     */
    public String getHours() {

        Date startDate = this.getStartTime();
        Date stopDate = this.getStopTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String startingHour =  dateFormat.format(startDate);
        String endHour = dateFormat.format(stopDate);

        return startingHour + " - " + endHour;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventID);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.startTimestamp);
        dest.writeLong(this.stopTimestamp);
        dest.writeInt(this.type);
    }

    public static final Creator<ScheduleEvent> CREATOR = new Creator<ScheduleEvent>() {
        @Override
        public ScheduleEvent createFromParcel(Parcel source) {
            return new ScheduleEvent(source);
        }

        @Override
        public ScheduleEvent[] newArray(int size) {
            return new ScheduleEvent[size];
        }
    };
}
