package com.example.itp4229m.retrofit;

public class Cart {
    private String id;
    private Number price;
    private String course_id;
    private String user_id;
    private String title;
    private String thumbnail;
    private String instructor_name;

    //getter and setter
    public String getId() { return id; }

    public Number getPrice() { return price; }

    public String getCourseId() { return course_id; }
    public void setCourseId(String course_id) { this.course_id = course_id; }

    public String getUserId() { return user_id; }
    public void setUserId(String user_id) { this.user_id = user_id;}

    public String getTitle() { return title; }

    public String getThumbnail() { return thumbnail; }

    public String getInstructorName() { return instructor_name; }
}
