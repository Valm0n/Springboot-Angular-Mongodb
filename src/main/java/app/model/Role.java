package app.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ANONYMOUS("ROLE_ANONYMOUS"),
    USER("ROLE_USER"), 
    READER("ROLE_READER"), 
    WRITER("ROLE_WRITER"),
    ADMIN("ROLE_ADMIN");
    
    private static final Map<String, Role> rolesMap = new HashMap<String, Role>();
    static {
        for (Role r : Role.values()) {
            rolesMap.put(r.getLabel(), r);
        }
    }
    
    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    public static Role getRoleByLabel(String label){
        return rolesMap.get(label);
    }
}
