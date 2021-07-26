/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */


package ucf.assignments;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public class Validator {

    public boolean catchInvalidValue(String value) {
        // try to addItemToTable
        boolean flag = false;

        if(!Objects.equals(value, "")) {
            flag = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.show();
        }

        return flag;
    }

    // catch incorrectly formatted serial numbers
    public boolean catchInvalidSerial(String serialNumber) {
        // declare bool for a marker and initialize it to false
        boolean flag = false;

        // if inputted sn is 10 digits and contains only letters/numbers then change the bool marker to true
        if(serialNumber.matches("^[a-zA-Z0-9]{10}$")) {
            flag = true;
        } else {
            // else show an alert telling the user the format is incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Please enter a serial number in the format of XXXXXXXXXX where X can be either a letter or digit");
            alert.show();
        }
        // return bool marker
        return flag;
    }

    // check is serial number already exists in database
    public boolean validateSerialNumber(String serialNumber, boolean initialStatus, ArrayList<Item> theList, ImageView serialNumberImage) {
        // initialize boolean marker to true since we want it to loop through correctly the first time
        boolean flag = initialStatus;
        // for (all the items in the list)
        for(int i = 0; i < theList.size(); i++) {
            // if i serial number matches the inputted serial number
            if(theList.get(i).getSerialNumber().contains(serialNumber.toUpperCase())) {
                // show an alert telling the user the serial number already exists in the database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid input. Serial Number already exists in database");
                alert.show();
                // change the bool marker to false
                flag = false;
                break;
            } else {
                // bool marker true
                serialNumberImage.setVisible(false);
                flag = true;
            }

        }
        // return bool marker
        return flag;
    }

    public boolean validateString(String name) {
        boolean flag = true;
        if(name.length() < 2 || name.length() > 256) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Name must be between 2 and 256 characters");
            alert.show();
            flag = false;
        }
        return flag;
    }


}
