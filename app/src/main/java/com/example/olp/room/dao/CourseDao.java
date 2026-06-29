package com.example.olp.room.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.olp.room.model.Course;

import java.util.List;


@Dao
public interface CourseDao {
    @Insert
    void insert(Course course); // insert

    @Update
    public void updateCourse(Course course); // update

    @Delete
    public void deleteCourse(Course course); // delete

    @Query("SELECT * FROM course_table")
    LiveData<List<Course>> getAllCourses(); // search all

    @Query("SELECT * FROM course_table WHERE id = :courseId")
    List<Course> getAllById(int[] courseId); // search all by id

    @Query("DELETE FROM course_table WHERE id = :courseId")
    void deleteById(int courseId); // delete by id

    @Query("DELETE FROM course_table")
    void deleteAll(); // delete all
}
