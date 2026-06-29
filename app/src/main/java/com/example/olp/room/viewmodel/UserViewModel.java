package com.example.olp.room.viewmodel;
import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.olp.room.dao.UserDao;
import com.example.olp.room.database.AppDatabase;
import com.example.olp.room.model.User;

public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;
    private final LiveData<List<User>> userList; //livecycle variable
    public UserViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        userList = userDao.getAllUsers();
    }
    public LiveData<List<User>> getUserList() {
        //Toast.makeText(this.getApplication(), userList.toString() + " loaded", Toast.LENGTH_SHORT).show();
        return userList;
    }
    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
        Toast.makeText(this.getApplication(), user.username + " added", Toast.LENGTH_SHORT).show();
    }
    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.updateUser(user);
        });
        Toast.makeText(this.getApplication(), user.username + " updated", Toast.LENGTH_SHORT).show();
    }
    public void delete(User user){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deleteUser(user);
        });
        Toast.makeText(this.getApplication(), user.username + " deleted", Toast.LENGTH_SHORT).show();
    }

    //use sql
    public void deleteById(int userId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deleteById(userId);
        });
    }
    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deleteAll();
        });
        Toast.makeText(this.getApplication(), "Delete All", Toast.LENGTH_SHORT).show();
    }
}
