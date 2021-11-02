package OOP_Lab_8.program.domain.entity;

public class Tariff {
    private String name;
    private String operatorName;
    private int payroll;
    private float smsPrice;
    private CallPrice callPrice;
    private Parameters parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSmsPrice() {
        return smsPrice;
    }

    public void setSmsPrice(int smsPrice) {
        this.smsPrice = smsPrice;
    }

    public CallPrice getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(CallPrice callPrice) {
        this.callPrice = callPrice;
    }

    public int getPayroll() {
        return payroll;
    }

    public void setPayroll(int payroll) {
        this.payroll = payroll;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Tariff [name = " + name + ", smsPrice = " + smsPrice + ", callPrice = " + callPrice + ", payroll = " + payroll + ", operatorName = " + operatorName + ", parameters = " + parameters + "]";
    }
}
