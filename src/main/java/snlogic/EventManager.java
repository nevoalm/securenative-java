package snlogic;

import models.ActionResult;
import models.EventOptions;
import models.SnEvent;

import javax.servlet.http.HttpServletRequest;

public interface EventManager {
    SnEvent buildEvent(final HttpServletRequest request, final EventOptions options);
    ActionResult sendSync(SnEvent event, String requestUrl);
    void sendAsync(SnEvent event,String url);

}
