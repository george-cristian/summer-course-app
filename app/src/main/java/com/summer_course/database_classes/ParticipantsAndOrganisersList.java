package com.summer_course.database_classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Cristian
 *
 * Object that holds a list of participants and a list of organisers retrieved from the database.
 */
public class ParticipantsAndOrganisersList {

    private ArrayList<User> participantsList;
    private ArrayList<User> organisersList;

    public ParticipantsAndOrganisersList(ArrayList<User> participantsList, ArrayList<User> organisersList) {
        this.participantsList = participantsList;
        this.organisersList = organisersList;
    }

    public ArrayList<User> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(ArrayList<User> participantsList) {
        this.participantsList = participantsList;
    }

    public ArrayList<User> getOrganisersList() {
        return organisersList;
    }

    public void setOrganisersList(ArrayList<User> organisersList) {
        this.organisersList = organisersList;
    }
}
