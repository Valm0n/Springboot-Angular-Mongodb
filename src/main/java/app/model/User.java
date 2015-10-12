package app.model;

import java.util.ArrayList;

public class User {

    private String username;
    private ArrayList<Role> roles;

    public User() {
    }

    public User(ArrayList<Role> roles, String username) {
        this.username = username;
        this.roles = roles;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    
    public Boolean isAdmin(){
        return this.roles.contains(Role.ADMIN);
    }
    
    
}
