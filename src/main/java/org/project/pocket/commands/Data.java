package org.project.pocket.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.service.ApplicationPropertiesReader;

import java.util.Objects;

public abstract class Data {
    @JsonIgnore
    private String uri;
    @JsonProperty(value = "consumer_key")
    private String consumerKey;

    protected Data(String uri) {
        setUri(uri);
        this.consumerKey = ApplicationPropertiesReader.getProperty("pocket.consumerKey");
    }

    private void setUri(String uri) {
        this.uri = Objects.requireNonNull(uri);
    }

    public String getUri() {
        return uri;
    }


    //TODO delete
    public String getConsumerKey() {
        return consumerKey;
    }
}
