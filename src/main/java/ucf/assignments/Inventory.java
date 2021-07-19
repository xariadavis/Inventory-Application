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
