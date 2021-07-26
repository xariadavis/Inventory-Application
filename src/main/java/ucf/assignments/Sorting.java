/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

public class Sorting {

    public int sortValueASC(Item i1, Item i2) {
        return Double.compare(Double.parseDouble(i1.getValue()), Double.parseDouble(i2.getValue()));
    }

    public int sortValueDESC(Item i1, Item i2) {
        return Double.compare(Double.parseDouble(i2.getValue()), Double.parseDouble(i1.getValue()));
    }

    public int sortSNASC(Item i1, Item i2) {
        return CharSequence.compare(i1.getSerialNumber().toLowerCase(), i2.getSerialNumber().toLowerCase());
    }

    public int sortSNDESC(Item i1, Item i2) {
        return CharSequence.compare(i2.getSerialNumber().toLowerCase(), i1.getSerialNumber().toLowerCase());
    }

    public int sortNameASC(Item i1, Item i2) {
        return CharSequence.compare(i1.getName().toLowerCase(), i2.getName().toLowerCase());
    }

    public int sortNameDESC(Item i1, Item i2) {
        return CharSequence.compare(i2.getName().toLowerCase(), i1.getName().toLowerCase());
    }
}
