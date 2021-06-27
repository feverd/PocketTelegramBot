package org.project.pocket.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.project.entity.PocketAppCode;
import org.project.pocket.command.AppCodeCmd;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class AppCodeRequest {
    private AppCodeCmd appCodeCmd;

    public AppCodeRequest(AppCodeCmd appCodeCmd) {
        this.appCodeCmd = Objects.requireNonNull(appCodeCmd);
    }

    // Лучше выкинуть или обработать ?
    public PocketAppCode getAppCode() {
        PocketAppCode pocketAppCode = new PocketAppCode();
        HttpClient client = HttpClient.newHttpClient();


        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(appCodeCmd.getRequestUrl()))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(appCodeCmd)))
                    .header("Content-Type", "application/json; charset=UTF8")
                    .header("X-Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            ObjectMapper mapperFromJson = new ObjectMapper();
            pocketAppCode = mapperFromJson.readValue(response.body(), PocketAppCode.class);
            pocketAppCode.setConsumerKey(appCodeCmd.getConsumerKey());
            System.out.println(pocketAppCode.toString());


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pocketAppCode;
    }

}
