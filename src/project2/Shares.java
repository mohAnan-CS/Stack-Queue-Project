package project2;

public class Shares {

    private int numberOfShares;
    private double priceOfShare;
    private String nameOfCompany;
    private String dateOfPurchase;

    public Shares(int numberOfShares, double priceOfShare, String nameOfCompany, String dateOfPurchase) {
        this.numberOfShares = numberOfShares;
        this.priceOfShare = priceOfShare;
        this.nameOfCompany = nameOfCompany;
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getPriceOfShare() {
        return priceOfShare;
    }

    public void setPriceOfShare(double priceOfShare) {
        this.priceOfShare = priceOfShare;
    }

    public String getNameOfCompany() {
        return nameOfCompany;
    }

    public void setNameOfCompany(String nameOfCompany) {
        this.nameOfCompany = nameOfCompany;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public String toString() {
        return "[" +
                "numberOfShares=" + numberOfShares +
                ", priceOfShare=" + priceOfShare +
                ", nameOfCompany='" + nameOfCompany + '\'' +
                ", dateOfPurchase='" + dateOfPurchase + '\'' +
                ']';
    }

}
