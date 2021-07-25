/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import javafx.scene.control.TableView;

import java.util.ArrayList;

public class Inventory {
    String title = "Inventory";
    public ArrayList<Item> theList = new ArrayList<>();

    public Inventory(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        if(title == null) {
            title = "Inventory";
        } else {
            title = getTitle();
        }
        return title;
    }

    public ArrayList<Item> getTheList() {
        return this.theList;
    }

    public int compareItems(Item i1, Item i2) {

        return Double.compare(Double.parseDouble(i1.getValue()), Double.parseDouble(i2.getValue()));
    }

    public void sortList(TableView inventoryTable) {
        this.theList.sort((i1, i2) -> (compareItems(i1, i2)));
    }

    public void setTheList(ArrayList<Item> theList) {
        this.theList = theList;
    }
}
