/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    Sorting test = new Sorting();
    Item i1 = new Item("15.00", "1234567890", "Netflix");
    Item i2 = new Item("5.00", "AL8A5D7GL7", "Spotify");

    /* My GUI table does technically handle sorting BUT since I saved all my numerical data as
        strings, they are sorted as strings. For example $100.00 is less that $5.00 because
        1 comes before 5. I had to create my own sort and override the specific column's sort
        function.
    */

    @Test
    // sorts values in ascending order
    void sortValueASC() {
        // declare expected and set = to the difference between "15.00" and "5.00" which == 1
        int expected = 1;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortValueASC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }

    @Test
    void sortValueDESC() {
        // declare expected and set = to the difference between "5.00" and "15.00" which == -1
        int expected = -1;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortValueDESC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }

    @Test
    void sortSNASC() {
        // declare expected and set = to the difference between "1234567890" and "AL8A5D7GL7" which == -48
        // sorting using letters and numbers
        int expected = -48;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortSNASC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }

    @Test
    void sortSNDESC() {
        // declare expected and set = to the difference between "AL8A5D7GL7" and "1234567890" which == 48
        // sorting using letters and numbers
        int expected = 48;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortSNDESC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }

    @Test
    void sortNameASC() {
        // declare expected and set = to the difference between "Netflix" and "Spotify" which == -5
        int expected = -5;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortNameASC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }

    @Test
    void sortNameDESC() {
        // declare expected and set = to the difference between "Spotify" and "Netflix" which == 5
        int expected = 5;

        // declare actual and set = to the value that will be returned by the test function
        int actual = test.sortNameDESC(i1, i2);

        // assert that the expected and actual values are equal
        assertEquals(expected, actual);
    }
}