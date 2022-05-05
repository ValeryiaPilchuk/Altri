package com.example.altri;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("Schedule")
public class Schedule extends ParseObject {

    public static final String KEY_TASK_NAME = "taskname";
    public static final String KEY_TASK_DESCRIPTION = "taskdescription";
    public static final String KEY_USER = "username";
    public static final String KEY_TASK_DATE = "date";
    public static final String KEY_TASK_TIME = "time";
    public static final String KEY_TASK_TIME_NUMBER = "timeNumber";
    public static final String KEY_TASK_COMPLETED = "completed";

 /*   public Schedule(String KEY_TASK_NAME, String KEY_TASK_DESCRIPTION, String KEY_USER, String KEY_TASK_DATE, String KEY_TASK_TIME) {
        this.KEY_TASK_NAME = KEY_TASK_NAME;
        this.KEY_TASK_DESCRIPTION = courseDescription;
        this.courseDuration = courseDuration;
    }
*/
    public String getTaskName() {
        return getString(KEY_TASK_NAME);
    }

    public void setTaskName(String taskName) {
        put(KEY_TASK_NAME, taskName);
    }

    public String getTaskDescription() {
        return getString(KEY_TASK_DESCRIPTION);
    }

    public void setTaskDescription(String taskDescription) {
        put(KEY_TASK_DESCRIPTION , taskDescription);
    }

    public String getTaskDate() {
        return getString(KEY_TASK_DATE);
    }

    public void setTaskDate(String taskDate) {
        put(KEY_TASK_DATE, taskDate);
    }

    public String getTaskTime() {
        return getString(KEY_TASK_TIME);
    }

    public void setTaskTime(String taskTime) {
        put(KEY_TASK_TIME, taskTime);
    }

    public Integer getTaskTimeNumber() {
        return getInt(KEY_TASK_TIME_NUMBER);
    }

    public void setTaskTimeNumber(String taskTimeNumber) {
        put(KEY_TASK_TIME_NUMBER, taskTimeNumber);
    }

    public String getUser() {
        return getString(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER , user);
    }

    public String getStatus() {
        return getString(KEY_TASK_COMPLETED);
    }

}
