package com.summer_course.utils;

import android.app.Application;

import com.summer_course.database_classes.User;

public class SummerCourseApplication extends Application {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isCurrentUserValidated() {
        return currentUser.isValidated();
    }

    public int getCurrentUserType() {
        return currentUser.getType();
    }
}
