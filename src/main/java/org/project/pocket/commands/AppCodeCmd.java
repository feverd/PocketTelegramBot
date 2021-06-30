package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AppCodeCmd {
    @JsonIgnore
    private final String requestUrl;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;
    @JsonProperty(value = "redirect_uri")
    private String redirectUri;

    public AppCodeCmd(String consumerKey, String redirectUri) {
        this.requestUrl = "https://getpocket.com/v3/oauth/request";
        setConsumerKey(consumerKey);
        this.redirectUri = redirectUri;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    private void setConsumerKey(String consumerKey) {
        this.consumerKey = Objects.requireNonNull(consumerKey);
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
