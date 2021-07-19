/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import java.util.ArrayList;

public class TableOperations {
    public Item addToTable(double value, String serialNumber, String name, ArrayList<Item> theList) {
        Item item = new Item(value, serialNumber, limitText(name));
        item.setValue(value);
        item.setSerialNumber(serialNumber);
        item.setName(limitText(name));
        theList.add(item);

        return item;
    }

    public String limitText(String itemName) {
        // get the event description
        String name = itemName;

        // if it is more than 256 chars return a split substring
        if(itemName.length() > 256) {
            name = itemName.substring(0, 256);
        }
        return name;
    }

    // remove item
    public void removeItem(int index, ArrayList<Item> theList) {
        // remove item at index specified
        theList.remove(index);
    }

    /*// editing event description and due date
    public void editListItem(int index, String editedEventDescription, LocalDate editedDate, ArrayList<Item> theList) {
        // uses the index of the selected item and sets the description using the text written into editedEventDescription field
        theList.get(index).setDescription(editedEventDescription);
        // use the index of the item we want to edit and set the new dueDate
        theList.get(index).setDueDate(editedDate);
    }*/

    public void editItem(int index, double editedVal, String editedSerialNum, String editedName) {

    }

}
