package snlogic;

import models.EventOptions;
import models.SecureNativeOptions;
import models.SnEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SnEventManagerTest {
    EventManager snEventManager;
    HttpServletRequest request;
    Utils utils;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        utils = mock(Utils.class);

    }

    @Test(expected = Exception.class)
    public void buildEventMangerWithNullOptions() throws Exception {
        snEventManager = new SnEventManager("1234", null);
    }

    @Test(expected = Exception.class)
    public void buildEventMangerWithNullApiKey() throws Exception {
        snEventManager = new SnEventManager(null, new SecureNativeOptions());
    }

    @Test
    public void buildEventTest() throws Exception {
        snEventManager = new SnEventManager("key",new SecureNativeOptions());
        when(request.getRemoteAddr()).thenReturn("address");
        when(request.getHeader("header")).thenReturn("header");
        when(utils.getCookie(any(),anyString())).thenReturn("cookie");
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("k","v"),new Cookie("_sn","X3NuX3ZhbHVl")});
        when(utils.base64decode(anyString())).thenReturn("base");
        SnEvent snEvent = snEventManager.buildEvent(request,  new EventOptions("ip", "userAgent","eventType"));
        Assert.assertEquals("eventType", snEvent.getEvent());
    }

}
