/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    Validator test = new Validator();
    ArrayList<Item> expected = new ArrayList<>();
    ImageView serialNumberImage = new ImageView();



    private void setTestArrayList() {
        Item testArrayItem = new Item("700.99", "1234569870", "Laptop");
        expected.add(testArrayItem);
    }

    @Test
    // Each inventory item shall have a value representing its monetary value in US dollars
    void catchInvalidValue() {
        // call the test function and pass in a non numerical value for the value -- assert that it is false
        assertTrue(test.catchInvalidValue("$16.00"));
    }

    @Test
    //Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit
    void catchInvalidSerial() {
        // call the test function and pass in a valid serial number -- assertTrue
        assertTrue(test.catchInvalidSerial("1234567890"));
    }

    @Test
    // The application shall display an error message if the user enters an existing serial number for the new item
    void validateSerialNumber() {
        // set the test arraylist
        setTestArrayList();
        // call the test function and pass in a unique serial number (not in the test list) and assert true
        assertTrue(test.validateSerialNumber("1234567890", false, expected, serialNumberImage));
    }

    @Test
    // Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
    void stringUnder256() {
        // call test function and pass in a string that is 256 chars -- assertTrue
        assertTrue(test.validateString("abcgxprDMo8kNNry91IqwiSGqK9XaypHlGWoHeMZwbB55NOwMHSYW1PTfSrj16sg7uR1t8suCHGFHr1PBfASH6Thh7WUJVAevDkt0PLj8eSaoZFftQPEaMoRKRcckIkGMts0LlbVg6I2HFK4B8K2aZqakp2tjARlwfVaP8hDnInmaQ51MN5ZF47CU6pT7MMjPa4CqCw8si0rFtWQ9odGLKmHKGORUDhvQgyYEJiK47eh4JQufpczvtwfhkGrsWth"));
    }

    @Test
    // Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
    void stringOver2() {
        // call test function and pass in a string that is 2 chars -- assertTrue
        assertTrue(test.validateString("jh"));
    }
}