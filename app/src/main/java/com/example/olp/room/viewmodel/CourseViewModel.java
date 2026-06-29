package com.example.olp.room.viewmodel;
import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import com.example.olp.room.dao.CourseDao;
import com.example.olp.room.database.AppDatabase;
import com.example.olp.room.model.Course;

public class CourseViewModel extends AndroidViewModel {
    private CourseDao courseDao;
    private final LiveData<List<Course>> courseList;
    public CourseViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        courseList = courseDao.getAllCourses();
    }
    public LiveData<List<Course>> getUserList() {
        //Toast.makeText(this.getApplication(), courseList.toString() + " loaded", Toast.LENGTH_SHORT).show();
        return courseList;
    }
    public void insert(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.insert(course);
        });
        Toast.makeText(this.getApplication(), course.title + " added", Toast.LENGTH_SHORT).show();
    }
    public void update(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.updateCourse(course);
        });
    }
    public void deleteById(int courseId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.deleteById(courseId);
        });
    }
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            courseDao.deleteAll();
        });
        Toast.makeText(this.getApplication(), "Delete All", Toast.LENGTH_SHORT).show();
    }
}
