package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SharesFX {


    private SimpleStringProperty numberOfSharesFX = new SimpleStringProperty("");
    private SimpleStringProperty priceOfShareFX = new SimpleStringProperty("");
    private SimpleStringProperty nameOfCompanyFX = new SimpleStringProperty("");
    private SimpleStringProperty dateOfPurchaseFX = new SimpleStringProperty("");

    SharesFX(String nameOfCompanyFX, String numberOfSharesFX, String priceOfShareFX, String dateOfPurchaseFX) {
        this.nameOfCompanyFX.set(nameOfCompanyFX);
        this.numberOfSharesFX.set(numberOfSharesFX);
        this.dateOfPurchaseFX.set(dateOfPurchaseFX);
        this.priceOfShareFX.set(priceOfShareFX);
    }

    public String getNumberOfSharesFX() {
        return numberOfSharesFX.get();
    }

    public SimpleStringProperty numberOfSharesFXProperty() {
        return numberOfSharesFX;
    }

    public void setNumberOfSharesFX(String numberOfSharesFX) {
        this.numberOfSharesFX.set(numberOfSharesFX);
    }

    public String getPriceOfShareFX() {
        return priceOfShareFX.get();
    }

    public SimpleStringProperty priceOfShareFXProperty() {
        return priceOfShareFX;
    }

    public void setPriceOfShareFX(String priceOfShareFX) {
        this.priceOfShareFX.set(priceOfShareFX);
    }

    public String getNameOfCompanyFX() {
        return nameOfCompanyFX.get();
    }

    public SimpleStringProperty nameOfCompanyFXProperty() {
        return nameOfCompanyFX;
    }

    public void setNameOfCompanyFX(String nameOfCompanyFX) {
        this.nameOfCompanyFX.set(nameOfCompanyFX);
    }

    public String getDateOfPurchaseFX() {
        return dateOfPurchaseFX.get();
    }

    public SimpleStringProperty dateOfPurchaseFXProperty() {
        return dateOfPurchaseFX;
    }

    public void setDateOfPurchaseFX(String dateOfPurchaseFX) {
        this.dateOfPurchaseFX.set(dateOfPurchaseFX);
    }
}
