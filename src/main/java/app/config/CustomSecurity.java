/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.config;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * takes care of Roles hierarchy.
 * If the current user has a role higher than the required one, then he'll be allowed.
 * Useful when we have a lot of roles and don't want to write "hasRole('user','admin','...')"
 * 
 * @author vsimon
 */
@Component("CustomSecurity")
public class CustomSecurity {
    public boolean hasRole(String key) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userRoleValue = auth.getAuthorities().toArray()[0].toString();
        
        Role currentUserRole = Enum.valueOf(Role.class, userRoleValue);
        Role expectedRole = Enum.valueOf(Role.class, key);
        
        return currentUserRole.ordinal() >= expectedRole.ordinal();
    }
}

enum Role{
    ROLE_ANONYMOUS,
    ROLE_USER,
    ROLE_ADMIN;
}
