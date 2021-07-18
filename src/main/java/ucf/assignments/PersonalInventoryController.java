/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.DecimalFormat;


public class PersonalInventoryController {
    TableOperations ops = new TableOperations();
    Inventory inventory = new Inventory();
    Item item;

    @FXML private Stage stage;
    @FXML public TableColumn<Item, String> valueColumn = new TableColumn<>("Value");
    @FXML public TableColumn<Item, String> snColumn = new TableColumn<>("Serial Number");
    @FXML public TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
    @FXML private TableView<Item> inventoryTable;
    @FXML private TextField valueTF, snTF, nameTF;

    public void initialize() {
        final ObservableList<Item> data = FXCollections.observableArrayList(
                new Item(20.00, "67676767", "test test test"),
                new Item(44.00, "54465465", "test test"),
                new Item(67.00, "31246516", "test"),
                new Item(20.00, "67676767", "test test test"),
                new Item(44.00, "54465465", "test test"),
                new Item(67.00, "31246516", "test"),
                new Item(20.00, "67676767", "test test test"),
                new Item(44.00, "54465465", "test test"),
                new Item(67.00, "31246516", "test"),
                new Item(20.00, "67676767", "test test test"),
                new Item(44.00, "54465465", "test test"),
                new Item(67.00, "31246516", "test")
        );

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        snColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //inventoryTable.setItems(data);
    }


    // close the application window
    public void exitWindow(ActionEvent actionEvent) {
        // grab the stage window
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        // close the stage window
        stage.close();
    }

    // return doubleValue
    private double getValue() {
        return Double.parseDouble(valueTF.getText());
    }

    // catch invalid value input
    public void catchInvalidValue() {
        // try to addItemToTable
        try {
            addItemToTable();
        } // if numberformatexception encountered, the user entered a non numerical value for the value field
        catch(NumberFormatException e) {
            // catch it and show a warning prompting them to enter a numerical value
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.show();
        }
    }

    // catch incorrectly formatted serial numbers
    private boolean catchInvalidSerial() {
        // declare bool for a marker and initialize it to false
        boolean flag = false;

        // if inputted sn is 10 digits and contains only letters/numbers then change the bool marker to true
        if(snTF.getText().matches("^[a-zA-Z0-9]{10}$")) {
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

    // if user only enters int, then convert to proper double format
    private void formatTableview() {

        // format value column
        DecimalFormat currency = new DecimalFormat("$0.00");
        valueColumn.setCellValueFactory(cellData -> {
            String formattedCost = currency.format(getValue());
            return new SimpleStringProperty(formattedCost);
        });
    }

    // check is serial number already exists in database
    public boolean validateSerialNumber(String serialNumber) {
        // initialize boolean marker to true since we want it to loop through correctly the first time
        boolean flag = true;
        // for (all the items in the list)
        for(int i = 0; i < this.inventory.theList.size(); i++) {
            // if i serial number matches the inputted serial number
            if(this.inventory.theList.get(i).getSerialNumber().contains(serialNumber)) {
                // show an alert telling the user the serial number already exists in the database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid input. Serial Number already exists in database");
                alert.show();
                // change the bool marker to false
                flag = false;
                break;
            } else {
                System.out.println("I don't match :)");
                // bool marker true
                flag = true;
            }

            //if(!flag) {
                //Alert alert = new Alert(Alert.AlertType.ERROR);
                //alert.setContentText("Invalid input. Serial Number already exists in database");
                //alert.show();
            //}
        }

        // return bool marker
        return flag;
    }

    // add event to tableview
    public void addItemToTable() {
        // catchInvalidValue();
        // convert textfields to strings

        // if the serial number is not invalid/a duplicate, then add the event
        if(catchInvalidSerial() && validateSerialNumber(snTF.getText())) {

            // convert inputted into to strings
            String value = valueTF.getText();
            String sn = snTF.getText();
            String name = nameTF.getText();

            validateSerialNumber(sn);
            //System.out.println("validation here, im " + validateSerialNumber(sn));

            // create and item with the converted fields and call addToTable in ops to add it to the array list
            Item item = ops.addToTable(Double.parseDouble(value), sn, name, this.inventory.theList);

            // call formatTableView to format the currency correctly
            formatTableview();

            // add the created item to tableView
            inventoryTable.getItems().add(item);

            System.out.println(inventory.getTheList());
        }
    }
}
