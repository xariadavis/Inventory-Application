/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Item {
    private SimpleStringProperty value;
    private SimpleStringProperty serialNumber;
    private SimpleStringProperty name;
    FileManagement file = new FileManagement();

    Item(String value, String serialNumber, String name) {
        this.value = new SimpleStringProperty(value);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.name = new SimpleStringProperty(name);;
    }

    public Item (String rawData, String regex) {
        //String[] parsed = rawData.split("\\t");
        String[] parsed = rawData.split(regex);
        // where rawData is one line representing an Item in the format
        // double\tserialNumber\tname
        // ex: 123.0\tTESTING123\tnew list
        this.value = new SimpleStringProperty(parsed[0]);
        this.serialNumber = new SimpleStringProperty(parsed[1]);
        this.name = new SimpleStringProperty(parsed[2]);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
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
        return getValue() + "\t" + getSerialNumber() + "\t" + getName();
    }
}
