package snlogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import models.ClientFingurePrint;
import org.apache.http.conn.util.InetAddressUtils;
import org.springframework.web.util.WebUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private static String[] ipHeaders = {"x-forwarded-for", "x-client-ip", "x-real-ip", "x-forwarded", "x-cluster-client-ip", "forwarded-for", "forwarded", "via"};
    private static String COOKIE_NAME = "_sn";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static String EMPTY= "";

    public Utils() {
    }


    public String getCookie(HttpServletRequest request, String cookieName) {
        if (request == null){
            return null;
        }
        cookieName = Strings.isNullOrEmpty(cookieName) ? COOKIE_NAME : cookieName;
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie != null){
            return WebUtils.getCookie(request, cookieName).getValue();
        }
        return null;

    }

    public String base64decode(String encodedString) {
        if (Strings.isNullOrEmpty(encodedString)){
            return "";
        }
        return String.valueOf(Base64.getDecoder().decode(encodedString));
    }

    public ClientFingurePrint parseClientFP(String json) {
        if(Strings.isNullOrEmpty(json)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, ClientFingurePrint.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String remoteIpFromRequest(HttpServletRequest request) {
        if (request == null) {
            return EMPTY;
        }
        Optional<String> bestCandidate = Optional.empty();
        String header = "";
        for (int i = 0; i < ipHeaders.length; i++) {

            List<String> candidates = Arrays.asList();
            header = request.getHeader(ipHeaders[i]);
            if (!Strings.isNullOrEmpty(header)) {
                candidates = Arrays.stream(header.split(",")).map(s -> s.trim()).filter(s -> !Strings.isNullOrEmpty(s) &&
                        (InetAddressUtils.isIPv4Address(s) || InetAddressUtils.isIPv6Address(s)) &&
                        isPrivateIPAddress(s)).collect(Collectors.toList());
                if (candidates.size() > 0) {
                    return candidates.get(0);
                }
            }
            if (!bestCandidate.isPresent()) {
                bestCandidate = candidates.stream().filter(x -> isLoopBack(x)).findFirst();
            }
        }

        return "";
    }

    private boolean isLoopBack(String ip) {
        try {
            return InetAddress.getByName(ip).isLoopbackAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isPrivateIPAddress(String ipAddress) {
        InetAddress ia = null;
        try {
            InetAddress ad = InetAddress.getByName(ipAddress);
            byte[] ip = ad.getAddress();
            ia = InetAddress.getByAddress(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ia.isSiteLocalAddress();
    }

    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public String calculateRFC2104HMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }



}
