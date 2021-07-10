package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "tb_appCode")
public class PocketCode {
    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    @OneToOne(mappedBy = "pocketCode")
    @JsonIgnore
    private PocketUser pocketUser;

    @Column(nullable = false)
    @JsonIgnore
    private String consumerKey;
    @JsonProperty(value = "code")
    private String code;

    /**
     * Provided by Pocket API, but not stated in the documentation
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String state;



    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

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

    @Override
    public String toString() {
        return "PocketAppCode{" +
                "code='" + code + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                '}';
    }
}
