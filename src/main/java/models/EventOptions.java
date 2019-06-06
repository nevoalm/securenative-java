package models;

import java.util.Map;

public class EventOptions {
    private String ip;
    private String userAgent;
    private String remoteIP;
    private User user;
    private String device;
    private String cookieName;
    private String eventType;
    private Map params;

    public EventOptions(String ip, String remoteIP, String userAgent, String device, User user, String cookieName, String eventType, Map params) {
        this.ip = ip;
        this.remoteIP = remoteIP;
        this.userAgent = userAgent;
        this.device = device;
        this.user = user;
        this.cookieName = cookieName;
        this.eventType = eventType;
        this.params = params;
    }

    public EventOptions(String ip, String userAgent,String eventType) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.eventType = eventType;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }



}
