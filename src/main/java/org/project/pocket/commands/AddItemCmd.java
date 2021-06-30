package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddItemCmd implements Cmd{
    @JsonIgnore
    private final String uri;
    private String url;
    private String title;
    private String tags;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;
    @JsonProperty(value = "access_token")
    private String accessToken;

    public AddItemCmd(String url, String consumerKey, String accessToken) {
        this.uri = "https://getpocket.com/v3/add";
        this.url = url;
        setConsumerKey(consumerKey);
        this.accessToken = accessToken;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Objects.requireNonNull(url);
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    private void setConsumerKey(String consumerKey) {
        this.consumerKey = Objects.requireNonNull(consumerKey);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getUri() {
        return uri;
    }
}
