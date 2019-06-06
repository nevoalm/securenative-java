package models;

public class ActionResult {
    private ActionType.type action;
    private double riskScore;
    private String[] triggers;

    public ActionResult() {
    }

    public ActionResult(ActionType.type action, double riskScore, String[] triggers) {
        this.action = action;
        this.riskScore = riskScore;
        this.triggers = triggers;
    }

    public ActionType.type getAction() {
        return action;
    }

    public void setAction(ActionType.type action) {
        this.action = action;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public String[] getTriggers() {
        return triggers;
    }

    public void setTriggers(String[] triggers) {
        this.triggers = triggers;
    }
}