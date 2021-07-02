package org.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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

    private String code;

    /**
     * Provided by Pocket API, but not stated in the documentation
     */
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;


    public String getCode() {
        return code;
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
