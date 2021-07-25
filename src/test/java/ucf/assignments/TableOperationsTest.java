package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableOperationsTest {
    TableOperations test = new TableOperations();
    ArrayList<Item> expected = new ArrayList<>();
    ArrayList<Item> actual = new ArrayList<>();


    private void setTestArrayList() {
        Item testArrayItem = new Item("700.99", "1234569870", "Laptop");
        expected.add(testArrayItem);
    }

    @Test
    // The user shall be able to add a new inventory item
    void addToTable() {

        // set expected test list
        setTestArrayList();

        // call the addToTable method with test params
        test.addToTable("700.99", "1234569870", "Laptop", actual);

        // assert that the two arrays are equal
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void removeItem() {
        // set test list
        setTestArrayList();

        // remove item from test list
        expected.remove(0);

        // declare item to add to our actual list
        Item actualItem = new Item("7.00", "7854961247", "Fan");

        // add the item to our
        actual.add(actualItem);

        //call the removeLabel method with the test params
        test.removeItem(actualItem, actual);

        // assert that the two arrays are null
        assertEquals(expected, actual);
    }
}