package com.summer_course.database_classes;

/**
 * @author George Cristian
 *
 * Defines how a user is represented in the Firebase Database. This class is used when a new
 * user is written in the database.
 *
 */
public class User {

    private int type;
    private boolean validated;
    private String name;
    private String profilePicString;

    public User() {
        this.type = 0;
        this.validated = false;
        this.name = "";
        this.profilePicString = "";
    }

    public User(int type, boolean validated, String name, String profilePicString) {
        this.type = type;
        this.validated = validated;
        this.name = name;
        this.profilePicString = profilePicString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicString() {
        return profilePicString;
    }

    public void setProfilePicString(String profilePicString) {
        this.profilePicString = profilePicString;
    }
}
