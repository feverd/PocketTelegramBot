package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddItemData extends Data {
    private String url;
    private String title;
    private String tags;
    @JsonProperty(value = "access_token")
    private String accessToken;

    public AddItemData(String url, String accessToken) {
        super("https://getpocket.com/v3/add");
        setUrl(url);
        setAccessToken(accessToken);
    }

    private void setUrl(String url) {
        this.url = Objects.requireNonNull(url);
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = Objects.requireNonNull(accessToken);
    }

}
