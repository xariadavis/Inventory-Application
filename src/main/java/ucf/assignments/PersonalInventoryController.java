/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonalInventoryController {
    TableOperations ops = new TableOperations();
    Inventory inventory = new Inventory();
    FileManagement fileManagement = new FileManagement();
    ArrayList<Double> values = new ArrayList<>();

    @FXML public TableColumn<Item, String> valueColumn = new TableColumn<>("Value");
    @FXML public TableColumn<Item, String> snColumn = new TableColumn<>("Serial Number");
    @FXML public TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
    @FXML public TableColumn<Item, Boolean> deleteColumn = new TableColumn<>("");
    @FXML private TableView<Item> inventoryTable;
    @FXML private TextField valueTF, snTF, nameTF, searchBox, totalTF, snWordCount, nameWordCount;
    @FXML ImageView serialNumberImage, nameImage;
    @FXML JFXButton clearListButton, importButton, exportButton;

    public void initialize() {

        deleteColumn.setEditable(false);

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        snColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        inventoryTable.setEditable(true);
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        snColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        Callback<TableColumn<Item, String>, TableCell<Item, String>> cellFactory
                = (TableColumn<Item, String> param) -> new EditingCell();
        valueColumn.setCellFactory(cellFactory);

        deleteColumn.setCellValueFactory(
                r -> new SimpleBooleanProperty(r.getValue() != null));

        deleteColumn.setCellFactory(
                r -> new RemoveTablecell(inventoryTable, inventory.getTheList(), values));

        inventoryTable.getColumns().add(deleteColumn);

        snTF.textProperty().addListener((observableValue, oldValue, newValue) -> snTF.setText(newValue.toUpperCase()));

        searchTable();

        // listener for serial number textfield
        snTF.textProperty().addListener(((observable, oldValue, newValue) -> { int s = newValue.length();
            snWordCount.setText("Character Count: " + s);
            serialNumberImage.setVisible(s == 10);
        }));

        // listener for name textfield
        nameTF.textProperty().addListener(((observable, oldValue, newValue) -> { int n = newValue.length();
            nameWordCount.setText("Character Count: " + n);
            nameImage.setVisible(n >= 2 && n <= 256);
        }));

        valueTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d)$") || !newValue.matches(".")) {
                valueTF.setText(newValue.replaceAll("[^\\d(.)$]", ""));
            }
        });
    }

    // close the application window
    public void exitWindow(ActionEvent actionEvent) {
        // grab the stage window
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        // close the stage window
        stage.close();
    }


    // create method to return true if item matches search text
    private boolean searchForItem(Item item, String searchText){
        return (item.getName().toLowerCase().contains(searchText.toLowerCase())) ||
                (item.getSerialNumber().toLowerCase().contains(searchText.toLowerCase()));
    }

    // loop through and create a list for the items that match the search text
    private ObservableList<Item> filterList(ArrayList<Item> list, String searchText){
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchForItem(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }

    // add listener to search box
    public void searchTable() {
        inventoryTable.refresh();

        searchBox.textProperty().addListener((observableValue, oldValue, newValue) ->
                inventoryTable.setItems(filterList(this.inventory.theList, newValue))
        );

        inventoryTable.refresh();
    }

    // populate total data
    public void totalField() {
        double total = values.stream().mapToDouble(Double::doubleValue).sum();
        String string = String.format("$%.2f", total);
        totalTF.setText(string);
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
    private boolean catchInvalidSerial(String serialNumber) {
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

    // if user only enters int, then convert to proper double format
    private void formatTableview() {
        // format value column
        DecimalFormat currency = new DecimalFormat("$0.00");

        valueColumn.setCellValueFactory(cellData -> {
            String oldString = cellData.getValue().getValue();
            String formattedCost;
            if(cellData.getValue().getValue().startsWith("$")) {
                formattedCost = oldString;
            } else if (cellData.getValue().getValue().matches("")) {
                formattedCost = "VALUE IS NULL";
            } else {
                formattedCost = currency.format(Double.parseDouble(cellData.getValue().getValue()));
            }
            return new SimpleStringProperty(formattedCost);

        });
    }

    // check is serial number already exists in database
    public boolean validateSerialNumber(String serialNumber, boolean initialStatus) {
        // initialize boolean marker to true since we want it to loop through correctly the first time
        boolean flag = initialStatus;
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
                // bool marker true
                serialNumberImage.setVisible(true);
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

    // refreshes the textfields so user does not have to manually reset each time
    public void refreshEvent() {
        // set value textfeild to null
        valueTF.clear();

        // set serial number value to null
        snTF.clear();

        // set name textfield to null
        nameTF.clear();
    }

    // add event to tableview
    public void addItemToTable() {

        // convert textfields to strings
        String value = valueTF.getText();
        String sn = snTF.getText();
        String name = nameTF.getText();


        // if the serial number is not invalid/a duplicate, then add the event
        if(catchInvalidSerial(sn) && validateSerialNumber(sn, true) && validateString(name)) {

            inventoryTable.refresh();

            validateSerialNumber(sn, true);

            // create and item with the converted fields and call addToTable in ops to add it to the array list
            Item item = ops.addToTable(value, sn, name, this.inventory.theList);

            values.add(Double.parseDouble(value));
            System.out.println(values);

            // call formatTableView to format the currency correctly
            formatTableview();

            // add the created item to tableView
            inventoryTable.getItems().add(item);

            refreshEvent();

            totalField();

            fileManagement.listToTXT("output/test.txt", inventory.theList);

            System.out.println(inventory.getTheList() + " after add");
        }
    }


    // validate input for edited value
    public void editItemValueInTable(TableColumn.CellEditEvent<Item, String> itemStringCellEditEvent) {

        // get item to edit
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        // set a variable to the old value -- set this if the user enters invalid input
        String oldValue = item.getValue();

        formatTableview();
        // try to set the value the user enters

        try {
            item.setValue(itemStringCellEditEvent.getNewValue());

            // if valid -- refresh the table so it can be formatted correctly per the formatTableValue function
            inventoryTable.refresh();

            totalField();
        } // if numberformatexception encountered, the user entered a non numerical value for the value field
        catch(NumberFormatException e) {
            // catch it and show a warning prompting them to enter a numerical value
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.show();
            item.setValue(oldValue);
            inventoryTable.refresh();
        }
    }

    // edit the serial number of an existing item
    public void editItemSNInTable(TableColumn.CellEditEvent<Item, String> itemStringCellEditEvent) {
        // get the item to edit
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        // set a variable to the old value -- set this if the user enters invalid input
        String oldSerialNumber = item.getSerialNumber();

        // if the edited value is in the correct format and not in the database
        if(catchInvalidSerial(itemStringCellEditEvent.getNewValue()) && validateSerialNumber(itemStringCellEditEvent.getNewValue(), false)) {
            // set it to item.setSerialNumber
            item.setSerialNumber(itemStringCellEditEvent.getNewValue().toUpperCase());
        } else {
            // else set the old serial number and refresh
            item.setSerialNumber(oldSerialNumber);
        }

        inventoryTable.refresh();
    }

    // edit the name of an existing item
    public void editItemNameInTable(TableColumn.CellEditEvent<Item, String> itemStringCellEditEvent) {
        // get the item
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        // set a variable to the old value -- set this if the user enters invalid input
        String oldName = item.getName();

        // if the new string name is in a valid format
        if(validateString(itemStringCellEditEvent.getNewValue())) {
            // set the edited string name
            item.setName(itemStringCellEditEvent.getNewValue());
        } else {
            // else set the old name and refresh the table
            item.setName(oldName);
            inventoryTable.refresh();
        }
    }

    // exports list as json to directory of choice
    public void exportButtonClicked() {
        // open filechooser
        FileChooser output = new FileChooser();
        // set extension filters
        output.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML file", "*.html"));
        output.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT file", "*.txt"));
        // show save file dialog
        File file = output.showSaveDialog(null);
        if (file != null && file.getAbsolutePath().endsWith(".html")) {
            // call the to outfile method in the list class and write to output
            fileManagement.listToHTML(file.getAbsolutePath(), inventory.getTheList());
        } else if(file != null && file.getAbsolutePath().endsWith(".txt")){
            fileManagement.listToTXT(file.getAbsolutePath(), inventory.getTheList());
        }
    }

    // import list to eventList
    public void importButtonClicked() {
        // open filechooser
        FileChooser output = new FileChooser();
        output.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT file", "*.txt"));
        // show open dialog
        File file = output.showOpenDialog(null);
        if (file != null) {
            fileManagement.TXTtoList(file.getAbsolutePath(), inventory.getTheList());
            inventoryTable.getItems().addAll(inventory.getTheList());
            System.out.println(inventory.getTheList() + "after import");
        }
    }
}
