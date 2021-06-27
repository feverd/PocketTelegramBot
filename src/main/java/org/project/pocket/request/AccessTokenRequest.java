package org.project.pocket.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.entity.PocketUser;
import org.project.pocket.command.AccessTokenCmd;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class AccessTokenRequest {
    private AccessTokenCmd accessCmd;

    public AccessTokenRequest(AccessTokenCmd accessCmd) {
        this.accessCmd = Objects.requireNonNull(accessCmd);
    }

    public PocketUser getUser() {
        PocketUser user = new PocketUser();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(accessCmd.getAuthorizeUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(accessCmd)))
                    .header("Content-Type", "application/json; charset=UTF8")
                    .header("X-Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            user = new ObjectMapper().readValue(response.body(), PocketUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }



}
