package snlogic;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;

@Service("snEventMAnager")
public class SnEventManager implements EventManager {
    private final String SN_COOKIE_NAME = "_sn";
    private final String USERAGENT_HEADER = "user-agent";
    private final String API_DEFAULT_URL = "https://api.securenative.com/v1/collector";
    private final String POST = "POST";
    private final String EMPTY = "";
    private FetchOptions defaultFetchOptions;
    private SecureNativeOptions options;
    private List<FetchOptions> events;
    private boolean sendEnabled = true;


    @Autowired
    public  Utils utils;

    public SnEventManager(){}
    public SnEventManager(SecureNativeOptions options) {
        this.defaultFetchOptions = new FetchOptions(options != null && options.getApiUrl() != null ? options.getApiUrl() : API_DEFAULT_URL, options.getApiKey(), POST, options.getTimeout());
        this.options = options;
        this.events = new ArrayList<>();
        startEventsPersist();
    }

    private void sendEvents() {
        if (this.events.size() > 0 && this.sendEnabled) {
            FetchOptions fetchEvent = this.events.remove(0);
            if (fetchEvent.fetch() == null) {
                this.events.add(0, fetchEvent);
                double backOff = Math.ceil(Math.random() * 10) * 1000;
                this.sendEnabled = false;
                this.setTimeout(() -> this.sendEnabled = true, (int) backOff);
            }
        }
    }

    @Override
    public SnEvent buildEvent(HttpServletRequest request, EventOptions options) {
        String decodedCookie = utils.base64decode(utils.getCookie(request, options != null && !Strings.isNullOrEmpty(options.getCookieName()) ? options.getCookieName() : SN_COOKIE_NAME));
        ClientFingurePrint clientFP = utils.parseClientFP(decodedCookie);
        String eventype = Strings.isNullOrEmpty(options.getEventType()) ? EventTypes.types.get(EventTypes.EventKey.LOG_IN) : options.getEventType();
        String cid = clientFP != null ? clientFP.getCid() : EMPTY;
        String vid = UUID.randomUUID().toString();
        String fp = clientFP != null ? clientFP.getFp() : EMPTY;
        String ip = options != null && options.getIp() != null ? options.getIp() : utils.remoteIpFromRequest(request);
        String remoteIP = request.getRemoteAddr();
        String userAgent = options != null && options.getUserAgent() != null ? options.getUserAgent() : request.getHeader(USERAGENT_HEADER);
        User user = options.getUser() != null ? options.getUser() : new User("anonymous", null, null);
        String device = options != null && options.getDevice() != null ? options.getDevice() : "";
        Map params = options != null && options.getParams() != null ? options.getParams() : new HashMap();
        return new SnEvent(eventype, cid, vid, fp, ip, remoteIP, userAgent, user, Instant.now().getEpochSecond(), device, params);
    }

    @Override
    public ActionResult sendSync(SnEvent event, String requestUrl) {
        FetchOptions fetchEvent = new FetchOptions(this.options.getApiUrl(), this.options.getApiKey(), this.defaultFetchOptions.getMethod(), this.options.getTimeout());
        String response = fetchEvent.fetch();
        if (response == null) {
            return new ActionResult(ActionType.type.ALLOW, 0.0, new String[0]);
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(response, ActionResult.class);
        } catch (Exception e) {
            System.err.println(e);
        }
        return new ActionResult(ActionType.type.ALLOW, 0.0, new String[0]);
    }

    @Override
    public void sendAsync(SnEvent event, String url) {
        if (this.events.size() >= this.options.getMaxEvents()) {
            this.events.remove(0);
        }
        this.events.add(0, new FetchOptions(this.options.getApiUrl(), this.options.getApiKey(), this.defaultFetchOptions.getMethod(), this.options.getTimeout()));
    }

    private void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    private void startEventsPersist() {
        if (this.options.isAutoSend()) {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    sendEvents();
                }
            }, 0, this.options.getInterval());
        }
    }
}

