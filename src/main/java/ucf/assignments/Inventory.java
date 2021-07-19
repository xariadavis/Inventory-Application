/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import java.util.ArrayList;

public class Inventory extends Item {
    public ArrayList<Item> theList = new ArrayList<>();

    public Inventory(){}

    public ArrayList<Item> getTheList() {
        return theList;
    }

    public void setTheList(ArrayList<Item> theList) {
        this.theList = theList;
    }
}
