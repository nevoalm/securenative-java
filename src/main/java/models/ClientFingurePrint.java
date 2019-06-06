package models;

public class ClientFingurePrint {
    private String cid;
    private String fp;

    public ClientFingurePrint(String cid, String fp) {
        this.cid = cid;
        this.fp = fp;
    }

    public ClientFingurePrint() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp;
    }
}
