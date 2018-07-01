package com.summer_course.database_classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author George Cristian
 *
 * Object that holds a list of participants and a list of organisers retrieved from the database.
 */
public class ParticipantsAndOrganisersList {

    private List<User> participantsList;
    private List<User> organisersList;

    public ParticipantsAndOrganisersList(List<User> participantsList, List<User> organisersList) {
        this.participantsList = participantsList;
        this.organisersList = organisersList;
    }

    public ArrayList<String> getParticipantsNames() {
        ArrayList<String> participantsNames = new ArrayList<>();

        for (User user : participantsList) {
            participantsNames.add(user.getName());
        }

        return participantsNames;
    }

    public ArrayList<String> getOrganisersNames() {
        ArrayList<String> organisersNames = new ArrayList<>();

        for (User user : organisersList) {
            organisersNames.add(user.getName());
        }

        return organisersNames;
    }

    public ArrayList<String> getParticipantsPics() {
        ArrayList<String> participantsPics = new ArrayList<>();

        for (User user : participantsList) {
            participantsPics.add(user.getProfilePicString());
        }

        return participantsPics;
    }

    public ArrayList<String> getOrganisersPics() {
        ArrayList<String> organisersPics = new ArrayList<>();

        for (User user : organisersList) {
            organisersPics.add(user.getProfilePicString());
        }

        return organisersPics;
    }

}
