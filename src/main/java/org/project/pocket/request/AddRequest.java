package org.project.pocket.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.entity.PocketUser;
import org.project.pocket.command.AddItemCmd;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddRequest {
    private AddItemCmd addItemCmd;

    public AddRequest(AddItemCmd addItemCmd) {
        this.addItemCmd = addItemCmd;
    }

    public boolean addItem() {
        boolean result = false;
        HttpClient client = HttpClient.newHttpClient();


        try {
            System.out.println(new ObjectMapper().writeValueAsString(addItemCmd));
            System.out.println("@@@@@@@@@@@@@@@@@@");

            HttpRequest request = HttpRequest.newBuilder(URI.create(addItemCmd.getAddUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(addItemCmd)))
                    .header("Content-Type", "application/json; charset=UTF8")
                    .header("X-Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) result = true;

            System.out.println(response.body());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
