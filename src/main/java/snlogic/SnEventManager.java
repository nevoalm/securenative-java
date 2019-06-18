package snlogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import models.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SnEventManager implements EventManager {
    private final String SN_COOKIE_NAME = "_sn";
    private final String USERAGENT_HEADER = "user-agent";
    private final String EMPTY = "";
    private final String USER_AGENT_VALUE = "snlogic.SecureNative-java";
    private final String SN_VERSION = "SN-Version";
    private final String SN_VERSION_VALUE = "";//TODO: figure out where the version come from maybe env var
    private SecureNativeOptions options;
    private CloseableHttpClient client;
    private String apiKey;
    private Utils utils;



    public SnEventManager(String apiKey, SecureNativeOptions options) throws Exception {
        if (isNullOrEmpty(apiKey) || options == null) {
            throw new Exception("You must pass your snlogic.SecureNative api key");

        }

        this.client = initializeHttpClient(options);
        this.options = options;
        this.apiKey = apiKey;
        utils = new Utils();
    }

    @Override
    public SnEvent buildEvent(HttpServletRequest request, EventOptions options) {
        String decodedCookie = utils.base64decode(utils.getCookie(request, options != null && !Strings.isNullOrEmpty(options.getCookieName()) ? options.getCookieName() : SN_COOKIE_NAME));
        ClientFingurePrint clientFP = utils.parseClientFP(decodedCookie);
        String eventype =  options == null || Strings.isNullOrEmpty(options.getEventType()) ? EventTypes.types.get(EventTypes.EventKey.LOG_IN) : options.getEventType();
        String cid = clientFP != null ? clientFP.getCid() : EMPTY;
        String vid = UUID.randomUUID().toString();
        String fp = clientFP != null ? clientFP.getFp() : EMPTY;
        String ip = options != null && options.getIp() != null ? options.getIp() : utils.remoteIpFromRequest(request);
        String remoteIP = request.getRemoteAddr();
        String userAgent = options != null && options.getUserAgent() != null ? options.getUserAgent() : request.getHeader(USERAGENT_HEADER);
        User user = options != null && options.getUser() != null ? options.getUser() : new User("anonymous", null, null);
        String device = options != null && options.getDevice() != null ? options.getDevice() : "";
        Map params = options != null && options.getParams() != null ? options.getParams() : new HashMap();
        return new SnEvent(eventype, cid, vid, fp, ip, remoteIP, userAgent, user, Instant.now().getEpochSecond(), device, params);
    }

    @Override
    public ActionResult sendSync(SnEvent event, String requestUrl) {
        ObjectMapper mapper = new ObjectMapper();
        String stringEvent = null;
        String line;
        try {
            stringEvent = mapper.writeValueAsString(event);

            HttpPost httpPost = new HttpPost(requestUrl);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION,this.apiKey);
            httpPost.addHeader(USERAGENT_HEADER,USER_AGENT_VALUE);
            httpPost.addHeader(SN_VERSION,getVersion());

            httpPost.setEntity(new StringEntity(stringEvent));

            HttpResponse response = this.client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() > 210){
                new ActionResult(ActionType.type.ALLOW, 0.0, new String[0]);
            }
            BufferedReader rd = new BufferedReader(
                   new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return mapper.readValue(result.toString(), ActionResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ActionResult(ActionType.type.ALLOW, 0.0, new String[0]);
    }

    @Override
    public void sendAsync(SnEvent event, String url) {
        //TODO: will be implemented in future version
    }

    private void setTimeout(Runnable runnable, int delay) { // Will be used in sendAsync
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    private CloseableHttpClient initializeHttpClient(SecureNativeOptions options){
        return HttpClients.custom().setUserAgent(USER_AGENT_VALUE)
                .setConnectionTimeToLive(options.getTimeout(), TimeUnit.MILLISECONDS)
                .setDefaultHeaders(Arrays.asList(new BasicHeader(SN_VERSION, SN_VERSION_VALUE),
                        new BasicHeader(HttpHeaders.AUTHORIZATION, this.apiKey)))
                .build();
    }

    private String getVersion(){
        try{
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model read = reader.read(new FileReader("pom.xml"));
            return read.getVersion();
        }
        catch (Exception e){
            return "unknown";
        }


    }
}

