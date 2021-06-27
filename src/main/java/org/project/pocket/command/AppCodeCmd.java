package org.project.pocket.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppCodeCmd {
    @JsonIgnore
    private final String requestUrl;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;
    @JsonProperty(value = "redirect_uri")
    private String redirectUri;

    public AppCodeCmd(String consumerKey, String redirectUri) {
        this.requestUrl = "https://getpocket.com/v3/oauth/request";
        this.consumerKey = consumerKey;
        this.redirectUri = redirectUri;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
