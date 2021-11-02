package OOP_Lab_8.program.domain.entity;

public class CallPrice {
    private float outsideTheNetwork;
    private float toLandlinePhones;
    private float withinTheNetwork;

    public float getOutsideTheNetwork() {
        return outsideTheNetwork;
    }

    public void setOutsideTheNetwork(float outsideTheNetwork) {
        this.outsideTheNetwork = outsideTheNetwork;
    }

    public float getToLandlinePhones() {
        return toLandlinePhones;
    }

    public void setToLandlinePhones(float toLandlinePhones) {
        this.toLandlinePhones = toLandlinePhones;
    }

    public float getWithinTheNetwork() {
        return withinTheNetwork;
    }

    public void setWithinTheNetwork(int withinTheNetwork) {
        this.withinTheNetwork = withinTheNetwork;
    }

    @Override
    public String toString() {
        return "CallPrice [outsideTheNetwork = " + outsideTheNetwork + ", toLandlinePhones = " + toLandlinePhones + ", withinTheNetwork = " + withinTheNetwork + "]";
    }
}
