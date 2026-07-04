package com.example.itp4229m.retrofit;

public class Review {
    private String id;
    private float rating;
    private String comment;
    private String create_at;
    private String course_id;
    private String user_id;
    private String username;
    private String avatar;

    //getter and setter
    public String getId() { return id; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating;}

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getCreate_at() { return create_at; }

    public String getCourse_id() { return course_id; }
    public void setCourse_id(String course_id) { this.course_id = course_id; }

    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }

    public String getAvatar() { return avatar; }
}
