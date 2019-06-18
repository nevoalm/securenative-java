package snlogic;

import models.EventOptions;
import models.User;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SecureNativeTest {

    HttpServletRequest request;
    Utils utils;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        utils = mock(Utils.class);
        when(request.getRemoteAddr()).thenReturn("address");
        when(request.getHeader("header")).thenReturn("header");
        when(utils.getCookie(any(),anyString())).thenReturn("cookie");
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("k","v"),new Cookie("_sn","X3NuX3ZhbHVl")});
        when(utils.base64decode(anyString())).thenReturn("base");

    }

    @Test (expected = Exception.class)
    public void initializeSnWhenApiKeyNullTest()  throws Exception {
        SecureNative sn = new SecureNative(null, null);
    }


    @Test (expected = Exception.class)
    public void initializeSnWhenApiKeyEmptyTest()  throws Exception {
        SecureNative sn = new SecureNative("", null);
    }

    @Test
    public void initializeSnWhenApiKeyGoodAndOptionsNullTest()  throws Exception {
        SecureNative sn = new SecureNative("api", null);
    }

    @Test
    public void initializeSnTest()  throws Exception {
        SecureNative sn = new SecureNative("", null);
    }

    @Test(expected = Exception.class)
    public void callTrackWith7CustomParams()  throws Exception {
        SecureNative sn = new SecureNative("api", null);
        Map mymap = new HashMap<String, String>() {
            {
                put("one", "one");
                put("two", "two");
                put("three", "three");
                put("four", "four");
                put("five", "five");
                put("six", "six");
                put("seven", "seven");
            }
        };
        EventOptions options = new EventOptions("ip", "remoteIP", "userAgent" , "device", new User("1","name","email") , "cookieName" , "eventType", mymap);
        sn.track(options, request);


    }
}
