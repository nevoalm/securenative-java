import models.SnEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import snlogic.AppConfig;
import snlogic.EventManager;
import snlogic.Utils;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SnEventManagerTest {

    @Autowired
    EventManager snEventManager;

    HttpServletRequest request;
    Utils utils;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        utils = mock(Utils.class);

    }

    @Test
    public void buildEventWhenOptionsNullTest(){
        when(request.getRemoteAddr()).thenReturn("address");
        when(request.getHeader("header")).thenReturn("header");
        when(utils.getCookie(any(),anyString())).thenReturn("cookie");
        when(utils.base64decode(anyString())).thenReturn("base");

        SnEvent snEvent = snEventManager.buildEvent(request, null);


        Assert.assertEquals("Mock user name", snEvent);

    }

}
