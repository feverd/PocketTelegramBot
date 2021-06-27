package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_users")
public class PocketUser {
    @Id
    @JsonIgnore
    private Long chatId;
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "username")
    private String email;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PocketUser{" +
                "chatId=" + chatId +
                ", accessToken='" + accessToken + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
