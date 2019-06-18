package snlogic;

import models.ClientFingurePrint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class UtilsTest {

    @Autowired
    Utils utils;

    HttpServletRequest request;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);

    }

    @Test
    public void getCookieWhenRequestNullExpectNullTest(){
        String cookie = utils.getCookie(null, "");
        Assert.assertEquals(cookie,null);
    }


    @Test
    public void getCookieWhenCookieNameEmpytExpectDefaultTest(){
        Cookie[] cookies = {new Cookie("_sn","value")};
        when(request.getCookies()).thenReturn(cookies);
        String cookie = utils.getCookie(request, "");
        Assert.assertEquals(cookie,"value");
    }


    @Test
    public void parseClientFPWhenJsonValid(){
        String validJson = "{\"cid\": \"cid\",\"fp\": \"fp\" }";
        ClientFingurePrint clientFingurePrint = utils.parseClientFP(validJson);
        Assert.assertEquals(clientFingurePrint.getCid(),"cid");
        Assert.assertEquals(clientFingurePrint.getFp(),"fp");
    }

    @Test
    public void parseClientFPWhenInvalidJson(){
        String validJson = "{\"cid\": \"cid\",\"fp\": \"fp\", }";
        ClientFingurePrint clientFingurePrint = utils.parseClientFP(validJson);
        Assert.assertEquals(clientFingurePrint,null);
    }

    @Test
    public void remoteIpFromRequestWhenRequestNullExpectEmptyString(){
        Assert.assertEquals(utils.remoteIpFromRequest(null),"");
    }










}
