package com.example.olp.room.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String username, passWord;

    public String firstName, lastName, email, avatar, bio,
            facebookUrl, instagramUrl, linkedinUrl, twitterUrl;

    public boolean isSuperuser, isInstructor;

    public User(@NonNull String username, @NonNull String passWord, String firstName, String lastName,
                String email, String avatar, String facebookUrl, String instagramUrl,
                String linkedinUrl, String twitterUrl, String bio,
                boolean isSuperuser, boolean isInstructor) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = passWord;
        this.email = email;
        this.avatar = avatar;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.twitterUrl = twitterUrl;
        this.bio = bio;
        this.isSuperuser = isSuperuser;
        this.isInstructor = isInstructor;
    }
}
