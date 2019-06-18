package snlogic;

import models.ActionResult;
import models.EventOptions;

import javax.servlet.http.HttpServletRequest;


public interface ISDK {
    void track(EventOptions options, HttpServletRequest request) throws Exception;
    ActionResult verify(EventOptions options, HttpServletRequest request);
    ActionResult flow(long flowId, EventOptions options, HttpServletRequest request);
    String getApiKey();

}
