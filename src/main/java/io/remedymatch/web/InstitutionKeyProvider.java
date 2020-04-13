package io.remedymatch.web;

import net.minidev.json.JSONArray;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
class InstitutionKeyProvider {

    String getInstitutionKey(){
        Jwt jwt  = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var groups =  (JSONArray) jwt.getClaims().get("groups");
        var group = groups.stream().map(Object::toString).filter(this::validateGroup).findFirst().get();
        return group;
    }

    private boolean validateGroup(String group){
        return !group.equals("offline_access") && !group.equals("uma_authorization");
    }

}
