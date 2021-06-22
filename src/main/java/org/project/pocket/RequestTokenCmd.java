package org.project.pocket;

public class RequestTokenCmd {
    private final String requestUrl;
    private String consumerKey;
    private String redirectUri;

    public RequestTokenCmd(String consumerKey, String redirectUri) {
        this.requestUrl = "https://getpocket.com/v3/oauth/request";
        this.consumerKey = consumerKey;
        this.redirectUri = redirectUri;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
