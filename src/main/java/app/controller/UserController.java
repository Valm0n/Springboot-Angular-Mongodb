package app.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class User {
    List<String> authorities; //: Array[4]
    String name; //: "admin"

    public User(List<String> authorities, String name) {
        this.authorities = authorities;
        this.name = name;
    }
    
    public User(){}

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}

@RestController
public class UserController {
    
    @RequestMapping("/user")
        public Principal user(Principal user) {
        return user;
    }
//    @RequestMapping("/user")
//    public User getCurrentUser(Principal user){
//        UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
//        ArrayList<String> listAuthorities = new ArrayList<>();
//        for(GrantedAuthority authority : userDetails.getAuthorities()){
//            listAuthorities.add(authority.getAuthority());
//        }
//        return new User(listAuthorities, userDetails.getName());
//    }
}
