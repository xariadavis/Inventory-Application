/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    private SimpleDoubleProperty value;
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty name;

    Item(double value, String serialNumber, String name) {
        this.value = new SimpleDoubleProperty(value);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.name = new SimpleStringProperty(name);
    }

    public Item() {
    }

    public double getValue() {
        //BigDecimal bd = new BigDecimal(value.get()).setScale(2, RoundingMode.HALF_UP);
        //double val2 = bd.doubleValue();
        //return val2;

        //return Math.round(value.get()*100.0)/100.0;
        return value.get();
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber.set(serialNumber);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String toString() {

        return "Value: " + getValue() + "; Serial Number: " + getSerialNumber() + "; Name: " + getName();
    }
}
