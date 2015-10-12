package app.controller;

import app.model.Role;
import app.model.User;
import java.security.Principal;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @RequestMapping("/user")
        public User user(Principal user) {
        //return user;
            UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken)user;
            ArrayList<Role> listAuthorities = new ArrayList<>();
            for(GrantedAuthority auth : userDetails.getAuthorities()){
                listAuthorities.add(Role.getRoleByLabel(auth.getAuthority()));
            }
            return new User(listAuthorities, userDetails.getName());
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
