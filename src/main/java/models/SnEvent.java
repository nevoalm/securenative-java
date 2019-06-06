package models;

import java.util.Map;

public class SnEvent {
    private String event;
    private String cid;
    private String vid;
    private String fp;
    private String ip;
    private String remoteIP;
    private String userAgent;
    private User user;
    private long ts;
    private String device;
    private Map<String,String> params;

    public SnEvent(String event, String cid, String vid, String fp, String ip, String remoteIP, String userAgent, User user, long ts, String device, Map<String,String> params) {
        this.event = event;
        this.cid = cid;
        this.vid = vid;
        this.fp = fp;
        this.ip = ip;
        this.remoteIP = remoteIP;
        this.userAgent = userAgent;
        this.user = user;
        this.ts = ts;
        this.device = device;
        this.params = params;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
