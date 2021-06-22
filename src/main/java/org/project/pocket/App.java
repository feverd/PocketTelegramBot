package org.project.pocket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*URL requestUrl = new URL("https://getpocket.com/v3/oauth/request");
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("x-accept", "application/json");
        connection.setRequestProperty("content-type", "application/json; charset=UTF-8");
        try (OutputStream stream = connection.getOutputStream()) {
            stream.write("97779-9e5f86561e627ae0c473d550".getBytes());
            stream.flush();
        }
        System.out.println(connection.getResponseCode());*/


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://getpocket.com/v3/oauth/request"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"consumer_key\":\"97779-9e5f86561e627ae0c473d550\",\"redirect_uri\":\"https://t.me/postItInst_bot\"}"))
                .header("Content-Type", "application/json; charset=UTF8")
                .header("X-Accept", "application/json; charset=UTF8")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.headers());
        System.out.println(response.statusCode());
        //System.out.println(response.body());

        System.out.println("https://getpocket.com/auth/authorize?request_token="+ response.body() + "&redirect_uri=" + "https://t.me/postItInst_bot");

        /*request = HttpRequest.newBuilder(URI.create("https://getpocket.com/v3/oauth/authorize"))
                .POST(HttpRequest.BodyPublishers.ofString("{\"consumer_key\":\"97779-9e5f86561e627ae0c473d550\",\"code\":\"" + response.body()+ "\"}"))
                .header("Content-Type", "application/json; charset=UTF8")
                .header("X-Accept", "application/json")
                .build();


        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
*/


    }
}
