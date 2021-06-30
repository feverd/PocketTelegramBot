package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tb_users")
public class PocketUser {
    @Id
    @JsonIgnore
    @Column(name = "chatId")
    private Long id;
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "username")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
