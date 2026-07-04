package com.example.itp4229m.retrofit;

public class Enrollment {
    private String id;
    private String enrolled_at;
    private int is_completed;
    private String course_id;
    private String user_id;

    //getter and setter
    public String getId() { return id; }

    public String getEnrolledAt() { return enrolled_at; }

    public int getIsCompleted() { return is_completed; }

    public String getCourseId() { return course_id; }
    public void setCourseId(String course_id) { this.course_id = course_id; }

    public String getUserId() { return user_id; }

    public void setUserId(String user_id) { this.user_id = user_id;}

}
