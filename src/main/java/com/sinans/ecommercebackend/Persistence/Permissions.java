package com.sinans.ecommercebackend.Persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permissions {

    USER_BROWSE("user:browse"),
    USER_CART("user:cart"),
    USER_ORDER("user:order"),
    
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin_update"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_CONTROL("admin:control");
    

    @Getter
    private final String permission;
}
