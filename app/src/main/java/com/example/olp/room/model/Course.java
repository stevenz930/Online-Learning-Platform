package com.example.olp.room.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public String description, level, thumbnail, createdAt, updatedAt;

    public double price;

    public Course(@NonNull String title, String description, String level,
                  String thumbnail, String createdAt, String updatedAt,
                  double price) {
        this.title = title;
        this.description = description;
        this.level = level;
        this.thumbnail = thumbnail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
    }
}
