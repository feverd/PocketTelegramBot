package org.project.pocket.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenCmd {
    @JsonIgnore
    private final String authorizeUrl;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;
    @JsonProperty(value = "code")
    private String code;

    public AccessTokenCmd(String consumerKey, String code) {
        this.authorizeUrl = "https://getpocket.com/v3/oauth/authorize";
        this.consumerKey = consumerKey;
        this.code = code;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
