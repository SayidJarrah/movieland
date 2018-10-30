package com.dkorniichuk.movieland.entity;

public class Token {
    private String uuid;
    private String nickname;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uuid='" + uuid + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
