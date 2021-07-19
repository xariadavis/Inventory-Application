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

}
