package models;

import org.apache.http.message.BasicHeader;

import java.util.List;

public class FetchOptions {
    private String apiKey;
    private String url;
    private String method;
    private List<BasicHeader> headers;

    public FetchOptions(String apiKey, String url, String method, List<BasicHeader> headers) {
        this.apiKey = apiKey;
        this.url = url;
        this.method = method;
        this.headers = headers;

    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<BasicHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<BasicHeader> headers) {
        this.headers = headers;
    }
}
