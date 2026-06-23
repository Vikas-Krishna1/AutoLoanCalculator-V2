package models;

import java.sql.Date;   
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)


public class User {
  
    private int user_id;
    private String username;
    private String password;
    private String role;
    private Date created_at;
    public User() {}

    public User(int user_id, String username, String password, String role, Date created_at) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
    }
//Getters
    public int getUser_id() {
        return user_id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    //Setters
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

    
}
