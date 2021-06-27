package org.project.pocket.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddItemCmd {
    @JsonIgnore
    private final String addUrl;
    private String url;
    private String title;
    private String tags;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;
    @JsonProperty(value = "access_token")
    private String accessToken;

    public AddItemCmd(String url, String consumerKey, String accessToken) {
        this.addUrl = "https://getpocket.com/v3/add";
        this.url = url;
        this.consumerKey = consumerKey;
        this.accessToken = accessToken;
    }

    public String getAddUrl() {
        return addUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
