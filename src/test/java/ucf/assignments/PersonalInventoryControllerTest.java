/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalInventoryControllerTest {
    PersonalInventoryController test = new PersonalInventoryController();
    Item i1 = new Item("15.00", "1234567890", "Netflix");
    Item i2 = new Item("5.00", "AL8A5D7GL7", "Spotify");

    @Test
        // Test whether application can store > 100 unique items
    void atLeast100Items() {

        // declare a new array list to hold the items
        ArrayList<Item> countItems = new ArrayList<>();

        // declare test int to add to end of item
        int testNum = 0;

        // create a for loop of i < 150 to create 150 items
        for(int i = 0; i < 150; i++) {
            // create a new item, at the end of one of the string + the testNum
            Item testArrayItem = new Item("4.99", "S77E5W9Q6S", "Hulu " + testNum);
            // add item to the test list
            countItems.add(testArrayItem);
            // increment testNum -- so a "unique" item gets added each time
            testNum++;
        }

        // assert equals that the list.size is equal to 150 items
        assertEquals(150, countItems.size());
    }


    @Test
    // the searchForItem returns true if the item matches the search
    // Test whether or not "Netf" relates to any component of item1
    void searchForItem_assertTrueName() {
        // call test function and pass in i1 and "Netf"
        // assert that it is true
        // since the test string, "Netf" is part of the Name in i1 -- it should be true
        assertTrue(test.searchForItem(i1, "Netf"));
    }

    @Test
        // the searchForItem returns true if the item matches the search
        // Test whether or not "Apple" relates to any component of item 2
    void searchForItem_assertFalseName() {
        // call test function and pass in i2 and "Apple"
        // assert that it is false
        // since the test string, "Apple" is NOT part of any component in item 2 -- it should be false
        assertFalse(test.searchForItem(i2, "Apple"));
    }

    @Test
        // the searchForItem returns true if the item matches the search
        // Test whether or not "5D7G" relates to any component of item2
    void searchForItem_assertTrueSN() {
        // call test function and pass in i2 and "5D7G"
        // assert that it is true
        // since the test string, "5D7G" is part of the SN in i2 -- it should be true
        assertTrue(test.searchForItem(i2, "5D7G"));
    }

    @Test
        // the searchForItem returns true if the item matches the search
        // Test whether or not "4321" relates to any component of item 1
    void searchForItem_assertFalseSN() {
        // call test function and pass in i1 and "4321"
        // assert that it is false
        // since the test string, "4321" is NOT part of any component in item 1 -- it should be false
        assertFalse(test.searchForItem(i1, "4321"));
    }

    @Test
    // nothing to test
    void editItemValueInTable() {
        // the tableview in GUI takes care of editing
        // onEditCommit it grabs the item and the new user input and updates the ArrayList<Item>
        // or shows an Alert if there is an error
    }

    @Test
    // nothing to test
    void editItemSNInTable() {
        // the tableview in GUI takes care of editing
        // onEditCommit it grabs the item and the new user input and updates the ArrayList<Item>
        // or shows an Alert if there is an error
    }

    @Test
    //  nothing to test
    void editItemNameInTable() {
        // the tableview in GUI takes care of editing
        // onEditCommit it grabs the item and the new user input and updates the ArrayList<Item>
        // or shows an Alert if there is an error
    }
}