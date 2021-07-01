package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AccessTokenData extends Data {
    @JsonProperty(value = "code")
    private String code;

    public AccessTokenData(String code) {
        super("https://getpocket.com/v3/oauth/authorize");
        setCode(code);
    }

    private void setCode(String code) {
        this.code = Objects.requireNonNull(code);
    }
}
