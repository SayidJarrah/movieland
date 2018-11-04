package com.dkorniichuk.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class AuthenticationToken {
    private UUID uuid;
    @JsonIgnore
    private String email;
    private String nickname;
    @JsonIgnore
    private UserRole userRole;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AuthenticationToken{" +
                "uuid=" + uuid +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
