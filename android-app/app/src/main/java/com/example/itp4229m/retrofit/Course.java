package com.example.itp4229m.retrofit;

public class Course {
    private String id;
    private String title;
    private String description;
    private Number price;
    private String created_at;
    private String updated_at;
    private int is_published;
    private String level;
    private String thumbnail;
    private float avg_rating;
    private String review_count;
    private String instructor_name;
    private String category_name;
    private String subject_name;
    private String tags;
    private String enrolled_at;
    private String total_lessons;
    private String completed_lessons;
    private String remaining_lessons;
    private String progress_percent;

    //getter and setter
    public String getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public Number getPrice() { return price; }

    public String getCreatedAt() { return created_at; }

    public String getUpdatedAt() { return updated_at; }

    public int getIsPublished() { return is_published; }

    public String getLevel() { return level; }

    public String getThumbnail() { return thumbnail; }

    public float getAvgRating() { return avg_rating; }

    public String getReviewCount() { return review_count; }

    public String getInstructorName() { return instructor_name; }

    public String getCategoryName() { return category_name; }

    public String getSubjectName() { return subject_name; }

    public String getTags() { return tags; }

    public String getEnrolledAt() { return enrolled_at; }

    public String getTotalLessons() { return total_lessons; }

    public String getCompletedLessons() { return completed_lessons; }

    public String getRemainingLessons() { return remaining_lessons; }

    public String getProgressPercent() { return progress_percent; }
}
