package com.example.itp4229m.retrofit;

public class User {
    private String id;
    private String password;
    private String last_login;
    private int is_superuser;
    private String first_name;
    private String last_name;
    private String date_joined;
    private String avatar;
    private String bio;
    private String email;
    private String username;
    private int is_instructor;
    private String facebook_url;
    private String instagram_url;
    private String linkedin_url;
    private String twitter_url;
    private int is_active;

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getLastLogin() { return last_login; }

    public int getIsSuperuser() { return is_superuser; }

    public String getFirstName() { return first_name; }

    public String getLastName() { return last_name; }

    public String getDateJoined() { return date_joined; }

    public String getAvatar() { return avatar; }

    public String getBio() { return bio; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getIsInstructor() { return is_instructor; }

    public String getFacebookUrl() { return facebook_url; }

    public String getInstagramUrl() { return instagram_url; }

    public String getLinkedinUrl() { return linkedin_url; }

    public String getTwitterUrl() { return twitter_url; }

    public int getIsActive() { return is_active; }
}

