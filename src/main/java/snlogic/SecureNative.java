package snlogic;

import com.google.common.base.Strings;
import models.ActionResult;
import models.EventOptions;
import models.SecureNativeOptions;
import models.SnEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SecureNative implements ISDK {
    private final int MAX_CUSTOM_PARAMS = 6;
    private final String API_URL = "https://api.securenative.com/collector/api/v1";
    private final int INTERVAL = 1000;
    private final int MAX_EVENTS = 1000;
    private final Boolean AUTO_SEND = true;
    private EventManager eventManager;

    public SecureNativeOptions getSnOptions() {
        return snOptions;
    }

    private SecureNativeOptions snOptions;

    public SecureNative(){}
    public SecureNative(String apiKey, SecureNativeOptions options) throws Exception {
        if (Strings.isNullOrEmpty(apiKey)) {
            throw new Exception("You must pass your snlogic.SecureNative api key");
        }

        this.snOptions = initializeOptions(apiKey, options);
        this.eventManager = new SnEventManager(this.snOptions);
    }


    private SecureNativeOptions initializeOptions(String apiKey, SecureNativeOptions options) {
        if (options == null) {
            options = new SecureNativeOptions();
        }
        options.setApiKey(apiKey);
        if (Strings.isNullOrEmpty(options.getApiUrl())) {
            options.setApiUrl(API_URL);
        }

        if (options.getInterval() == 0) {
            options.setInterval(INTERVAL);
        }

        if (options.getMaxEvents() == 0) {
            options.setMaxEvents(MAX_EVENTS);
        }
        if (options.isAutoSend() == null) {
            options.setAutoSend(AUTO_SEND);
        }
        return options;
    }

    @Override
    public void track(EventOptions options, HttpServletRequest request) throws Exception {
        if (options != null && options.getParams() != null && options.getParams().size() > MAX_CUSTOM_PARAMS) {
            throw new Exception("You can only specify maximum of " + MAX_CUSTOM_PARAMS + " params");
        }
        SnEvent event = this.eventManager.buildEvent(request, options);
        this.eventManager.sendAsync(event, this.snOptions.getApiUrl() + "/track");
    }

    @Override
    public ActionResult verify(EventOptions options, HttpServletRequest request) {
        SnEvent event = this.eventManager.buildEvent(request, options);
        return this.eventManager.sendSync(event, this.snOptions.getApiUrl() + "verify");
    }

    @Override
    public ActionResult flow(long flowId, EventOptions options, HttpServletRequest request) {
        SnEvent event = this.eventManager.buildEvent(request, options);
        return this.eventManager.sendSync(event, this.snOptions.getApiUrl() + "/flow/" + flowId);
    }
}