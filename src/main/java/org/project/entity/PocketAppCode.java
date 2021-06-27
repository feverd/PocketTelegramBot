package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tb_appCode")

public class PocketAppCode {
    @Id
    @Column(nullable = false)
    @JsonIgnore
    private String consumerKey;
    private String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String state;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @Override
    public String toString() {
        return "PocketAppCode{" +
                "code='" + code + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                '}';
    }
}
