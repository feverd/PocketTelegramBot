package org.project.pocket.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.entity.PocketCode;
import org.project.entity.PocketUser;
import org.project.pocket.data.AccessTokenData;
import org.project.pocket.data.AddItemData;
import org.project.pocket.data.AppCodeData;
import org.project.pocket.data.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PocketRequest {
    private HttpClient client;
    private ObjectMapper mapper;

    public PocketRequest() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    //TODO add response code check

    public PocketUser getPocketUser(AccessTokenData tokenCmd) {
        PocketUser user = new PocketUser();

        HttpResponse<String> response = sendRequest(client, tokenCmd);

        try {
            user = mapper.readValue(response.body(), PocketUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return user;
    }

    public PocketCode getAppCode(AppCodeData appCodeData) {
        PocketCode pocketCode = new PocketCode();

        HttpResponse<String> response = sendRequest(client, appCodeData);

        try {
            pocketCode = mapper.readValue(response.body(), PocketCode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //TODO delete
        pocketCode.setConsumerKey(appCodeData.getConsumerKey());
        System.out.println(pocketCode.toString());


        return pocketCode;
    }

    public boolean addItem(AddItemData addItemData) {
        boolean result = false;

        HttpResponse<String> response = sendRequest(client, addItemData);

        if (response.statusCode() == 200) result = true;

        return result;
    }

    private HttpRequest buildRequest(String uri, String body) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json; charset=UTF8")
                .header("X-Accept", "application/json")
                .build();

        return request;
    }

    private <T> HttpResponse<String> sendRequest(HttpClient client, Data data) {
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(buildRequest(data.getUri(),
                            new ObjectMapper().writeValueAsString((T) data)),
                            HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }


}
