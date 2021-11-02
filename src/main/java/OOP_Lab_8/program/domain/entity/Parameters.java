package OOP_Lab_8.program.domain.entity;

public class Parameters {
    private boolean isFavoriteNumberExist;
    private Tariffication tariffication;
    private int priceForConnection;

    public int getPriceForConnection() {
        return priceForConnection;
    }

    public void setPriceForConnection(int priceForConnection) {
        this.priceForConnection = priceForConnection;
    }

    public Tariffication getTariffication() {
        return tariffication;
    }

    public void setTariffication(Tariffication tariffication) {
        this.tariffication = tariffication;
    }

    public boolean getIsFavoriteNumberExist() {
        return isFavoriteNumberExist;
    }

    public void setIsFavoriteNumberExist(boolean isFavoriteNumberExist) {
        this.isFavoriteNumberExist = isFavoriteNumberExist;
    }

    @Override
    public String toString() {
        return "ClassPojo [priceForConnection = " + priceForConnection + ", tariffication = " + tariffication + ", isFavoriteNumberExist = " + isFavoriteNumberExist + "]";
    }
}
