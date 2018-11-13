package com.dkorniichuk.movieland.entity;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
    ROLE_ADMIN(2),
    ROLE_USER(1),
    ROLE_ANONYMOUS(3);

    private int value;
    private static final Map<Integer, UserRole> USER_ROLE_MAP = new HashMap<>();

    static {
        for (UserRole userRole : values()) {
            USER_ROLE_MAP.put(userRole.value, userRole);
        }
    }

    private UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole getByValue(int value) {
        return USER_ROLE_MAP.get(value);
    }
}
