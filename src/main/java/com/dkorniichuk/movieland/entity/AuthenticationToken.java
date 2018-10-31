package com.dkorniichuk.movieland.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthenticationToken {
    private UUID uuid;
    private String nickname;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime expireDate;


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

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uuid='" + uuid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", expireDate=" + expireDate +
                '}';
    }
}
