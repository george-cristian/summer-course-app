package com.summer_course.database_classes;

public class DatabaseEvent {

    private String title;
    private String description;

    private long start;
    private long stop;

    private int type;

    public DatabaseEvent(ScheduleEvent event) {
        this.title = event.getTitle();
        this.description = event.getDescription();

        this.start = event.getStartTimestamp();
        this.stop = event.getStopTimestamp();

        this.type = event.getType();
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStop() {
        return stop;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
