/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    private SimpleDoubleProperty value;
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty name;

    Item(double value, String serialNumber, String name) {
        this.value = new SimpleDoubleProperty(value);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.name = new SimpleStringProperty(name);;
    }

    public Item() {

    }

    public double getValue() {
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
