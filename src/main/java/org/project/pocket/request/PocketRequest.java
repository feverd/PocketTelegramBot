package org.project.pocket.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.entity.PocketAppCode;
import org.project.entity.PocketUser;
import org.project.pocket.commands.AccessTokenCmd;
import org.project.pocket.commands.AddItemCmd;
import org.project.pocket.commands.AppCodeCmd;
import org.project.pocket.commands.Cmd;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PocketRequest {

    //TODO add response code check

    public static PocketUser getPocketUser(AccessTokenCmd tokenCmd) {
        PocketUser user = new PocketUser();
        HttpClient client = HttpClient.newHttpClient();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client.
                    send(getRequest(tokenCmd.getAuthorizeUrl(),
                            mapper.writeValueAsString(tokenCmd)),
                            HttpResponse.BodyHandlers.ofString());

            user = mapper.readValue(response.body(), PocketUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static PocketAppCode getAppCode(AppCodeCmd appCodeCmd) {
        PocketAppCode pocketAppCode = new PocketAppCode();
        HttpClient client = HttpClient.newHttpClient();

        try {
            ObjectMapper mapper = new ObjectMapper();

            HttpResponse<String> response = client
                    .send(getRequest(appCodeCmd.getRequestUrl(),
                            mapper.writeValueAsString(appCodeCmd)),
                            HttpResponse.BodyHandlers.ofString());


            pocketAppCode = mapper.readValue(response.body(), PocketAppCode.class);
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

    public static boolean addItem(AddItemCmd addItemCmd) {
        boolean result = false;
        HttpClient client = HttpClient.newHttpClient();

        /*try {*/
            /*HttpResponse<String> response = client.
                    send(getRequest(addItemCmd.getAddUrl(),
                            new ObjectMapper().writeValueAsString(addItemCmd)),
                            HttpResponse.BodyHandlers.ofString());*/

            HttpResponse<String> response = sendRequest(client, addItemCmd);

            if (response.statusCode() == 200) result = true;

        /*} catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return result;
    }

    private static HttpRequest getRequest(String uri, String body) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json; charset=UTF8")
                .header("X-Accept", "application/json")
                .build();
        return request;
    }

    private static <T> HttpResponse<String> sendRequest(HttpClient client, Cmd cmd) {
        HttpResponse<String> response = null;
        try {

            response = client
                    .send(getRequest(cmd.getUri(),
                            new ObjectMapper().writeValueAsString((T) cmd)),
                            HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    /*private static <T> T getObjectFromJson(String jsonText, Object object, ObjectMapper mapper) {
        try {
            object = mapper.readValue(jsonText, object.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (T) object;
    }*/
}
