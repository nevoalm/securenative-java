package models;

public class SecureNativeOptions {
    private String apiUrl;
    private int interval;
    private long maxEvents;
    private long timeout;
    private Boolean autoSend;

    public SecureNativeOptions(){}

    public SecureNativeOptions(String apiUrl, int interval, long maxEvents, int timeout, boolean autoSend) {
        this.interval = interval;
        this.maxEvents = maxEvents;
        this.apiUrl = apiUrl;
        this.timeout = timeout;
        this.autoSend = autoSend;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public long getMaxEvents() {
        return maxEvents;
    }

    public void setMaxEvents(long maxEvents) {
        this.maxEvents = maxEvents;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Boolean isAutoSend() {
        return autoSend;
    }

    public void setAutoSend(Boolean autoSend) {
        this.autoSend = autoSend;
    }


}
