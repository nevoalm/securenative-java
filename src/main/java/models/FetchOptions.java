package models;


import com.google.common.base.Strings;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchOptions {
    private final String USER_AGENT = "User-Agent";
    private final String USER_AGENT_VALUE = "snlogic.SecureNative-java";
    private final String SN_VERSION = "SN-Version";
    private final String SN_VERSION_VALUE = "";//TODO: figure out where the version come from mayve env var
    private final String AUTHORIZATION = "Authorization";
    private final String DEFAULT_API_URL = "https://api.securenative.com/v1/collector";

    private String url;
    private HttpClient client;
    private String method;
    private String apiKey;
    private Integer timeout;

    public FetchOptions(String url, String apiKey, String method, Integer timeout) {
        this.url = Strings.isNullOrEmpty(url) ? DEFAULT_API_URL : url;
        this.apiKey = apiKey;
        this.method = method;
        this.timeout = timeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpClient getClient() {
        return client;
    }

    public void setClient(HttpClient client) {
        this.client = client;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



    public String fetch(){
        try {
            URL url = new URL(this.url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod(this.method);
            con.setConnectTimeout(this.timeout);
            con.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
            con.setRequestProperty(SN_VERSION,SN_VERSION_VALUE);
            con.setRequestProperty(AUTHORIZATION, this.apiKey);
            con.setDoOutput(true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();


        } catch (IOException  e) {
            e.printStackTrace();
        }
        return null;
    }



}
