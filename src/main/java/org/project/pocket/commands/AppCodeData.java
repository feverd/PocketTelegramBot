package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AppCodeData extends Data {
    @JsonProperty(value = "redirect_uri")
    private String redirectUri;

    public AppCodeData(String redirectUri) {
        super("https://getpocket.com/v3/oauth/request");
        setRedirectUri(redirectUri);
    }

    private void setRedirectUri(String redirectUri) {
        this.redirectUri = Objects.requireNonNull(redirectUri);
    }
}
