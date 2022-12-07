package project2;

public class DailyPrice {

    private String companyName;
    private double dailyPrice;

    public DailyPrice(String companyName, double dailyPrice) {
        this.companyName = companyName;
        this.dailyPrice = dailyPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    @Override
    public String toString() {
        return "[" +
                "companyName='" + companyName + '\'' +
                ", dailyPrice=" + dailyPrice +
                ']';
    }
}
