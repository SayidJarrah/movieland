package com.dkorniichuk.movieland.entity;

import java.util.HashMap;
import java.util.Map;

public enum UserRole {
    ADMIN_ROLE(2),
    USER_ROLE(1);

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
