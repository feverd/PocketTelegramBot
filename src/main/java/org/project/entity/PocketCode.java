package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tb_appCode")

public class PocketCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @OneToOne(mappedBy = "pocketCode")
    @JsonIgnore
    private PocketUser pocketUser;

    @Column(nullable = false)
    @JsonIgnore
    private String consumerKey;

    //@JsonIgnore
    //TODO подумать сделать связь или нет

    private String code;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;


    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public PocketUser getPocketUser() {
        return pocketUser;
    }

    public void setPocketUser(PocketUser pocketUser) {
        this.pocketUser = pocketUser;
    }

    public void setId(int id) {
        this.id = id;
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