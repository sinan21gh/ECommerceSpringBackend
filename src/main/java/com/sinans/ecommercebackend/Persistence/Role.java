package com.sinans.ecommercebackend.Persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    USER(Set.of(Permissions.USER_BROWSE,
            Permissions.USER_CART,
            Permissions.USER_ORDER
    )),
    ADMIN(Set.of(Permissions.ADMIN_UPDATE,
            Permissions.ADMIN_CREATE,
            Permissions.ADMIN_DELETE,
            Permissions.ADMIN_CONTROL,
            Permissions.USER_BROWSE));
    

    @Getter
    private final Set<Permissions> permissions;
    
    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
    
    
}
