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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PersonalInventoryController {
    TableOperations ops = new TableOperations();
    Inventory inventory = new Inventory();
    FileManagement fileManagement = new FileManagement();
    Sorting sorting = new Sorting();
    ArrayList<Double> values = new ArrayList<>();
    Validator validate = new Validator();


    @FXML public TableColumn<Item, String> valueColumn = new TableColumn<>("Value");
    @FXML public TableColumn<Item, String> snColumn = new TableColumn<>("Serial Number");
    @FXML public TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
    @FXML public TableColumn<Item, Boolean> deleteColumn = new TableColumn<>("");
    @FXML private TableView<Item> inventoryTable;
    @FXML private TextField valueTF, snTF, nameTF, searchBox, totalTF, snWordCount, nameWordCount, itemCount;
    @FXML ImageView serialNumberImage, nameImage;
    @FXML JFXButton clearListButton, importButton, exportButton;

    public void initialize() {

        Label placeholder = new Label();
        placeholder.setText("No items found in inventory.");
        placeholder.setTextFill(Color.valueOf("white"));
        placeholder.setOpacity(0.5);
        placeholder.setStyle("-fx-font-size: 14; -fx-font-family: 'Segoe UI Light', Regular;");
        inventoryTable.setPlaceholder(placeholder);

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

        valueColumn.sortTypeProperty().addListener(
                (newValue) -> sortVal());

        snColumn.sortTypeProperty().addListener(
                (newValue) -> sortSN());

        nameColumn.sortTypeProperty().addListener(
                (newValue) -> sortName());

        deleteColumn.setCellValueFactory(
                r -> new SimpleBooleanProperty(r.getValue() != null));

        deleteColumn.setCellFactory(
                r -> new RemoveTablecell(inventoryTable, inventory, totalTF, totalField(), itemCount));

        inventoryTable.getColumns().add(deleteColumn);

        snTF.textProperty().addListener((observableValue, oldValue, newValue) -> snTF.setText(newValue.toUpperCase()));

        setTotalTF(totalTF, "Add items to list to see total");
        setTotalCount(itemCount, "0 items");

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

    private void sortVal() {
        System.out.println("in sort");
        if(valueColumn.getSortType().equals(TableColumn.SortType.ASCENDING)){
            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortValueASC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                }

                System.out.println("VAL ASC: " + inventory.getTheList());

                return true;
            });

        } else if(valueColumn.getSortType().equals(TableColumn.SortType.DESCENDING)) {

            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortValueDESC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                } System.out.println("VAL DESC: " + inventory.getTheList());

                return true;
            });

        }


    }

    private void sortSN() {
        if(snColumn.getSortType().equals(TableColumn.SortType.ASCENDING)) {

            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortSNASC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                } System.out.println("SN ASC: " + inventory.getTheList());

                return true;
            });

        } else if(snColumn.getSortType().equals(TableColumn.SortType.DESCENDING)) {

            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortSNDESC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                } System.out.println("SN DESC: " + inventory.getTheList());

                return true;
            });

        }

    }

    private void sortName() {
        if(nameColumn.getSortType().equals(TableColumn.SortType.ASCENDING)) {
            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortNameASC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                } System.out.println("NAME ASC: " + inventory.getTheList());

                return true;
            });

        } else if(nameColumn.getSortType().equals(TableColumn.SortType.DESCENDING)) {
            inventoryTable.sortPolicyProperty().set(param -> {
                Comparator<Item> comparator = (i1, i2) -> sorting.sortNameDESC(i1, i2);
                FXCollections.sort(inventoryTable.getItems(), comparator);

                List<Item> list = inventoryTable.getItems();
                if (list instanceof ArrayList<?>) {
                    inventory.theList = (ArrayList<Item>) list;
                } else {
                    inventory.theList = new ArrayList<>(list);
                } System.out.println("NAME DESC: " + inventory.getTheList());

                return true;
            });

        }
    }

    // close the application window
    public void exitWindow(ActionEvent actionEvent) {
        // grab the stage window
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        // close the stage window
        stage.close();
    }


    // create method to return true if item matches search text
    public boolean searchForItem(Item item, String searchText){
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
                inventoryTable.getItems().setAll(filterList(this.inventory.theList, newValue)));
        System.out.println("hi uhh" + inventory.getTheList());
        inventoryTable.refresh();
    }

    // populate total data
    public String totalField() {
        double total = inventory.getTheList().stream().mapToDouble(item -> Double.parseDouble(item.getValue())).sum();
        return String.format("$%.2f", total);
    }

    public void setTotalTF(TextField totalTF, String total) {
        totalTF.setText(total);
    }

    public String setTotalItemCount() {
        return String.format("%d items", inventory.getTheList().size());
    }

    public void setTotalCount(TextField itemCount, String itemTotal) {
        itemCount.setText(itemTotal);
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

    // refreshes the textfields so user does not have to manually reset each time
    public void refreshEvent() {
        // set value textfeild to null
        valueTF.clear();

        // set serial number value to null
        snTF.clear();

        // set name textfield to null
        nameTF.clear();
    }

    public void showError(int errorNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (errorNumber) {
            case 1:
                alert.setContentText("Invalid input. Please enter a numerical value.");
                break;
            case 2:
                alert.setContentText("Invalid input. Please enter a serial number in the format of XXXXXXXXXX where X can be either a letter or digit");
                break;
            case 3:
                alert.setContentText("Invalid input. Serial Number already exists in database");
                break;
            case 4:
                alert.setContentText("Invalid input. Name must be between 2 and 256 characters");
                break;
        }
        alert.show();
    }

    // add event to tableview
    public void addItemToTable() {

        // convert textfields to strings
        String value = valueTF.getText();
        String sn = snTF.getText();
        String name = nameTF.getText();


        // if the serial number is not invalid/a duplicate, then add the event
        if(validate.catchInvalidValue(value) && validate.catchInvalidSerial(sn) && validate.validateSerialNumber(sn, true, this.inventory.getTheList(), serialNumberImage) && validate.validateString(name)) {

            inventoryTable.refresh();

            validate.validateSerialNumber(sn, true, this.inventory.getTheList(), serialNumberImage);

            // create and item with the converted fields and call addToTable in ops to add it to the array list
            Item item = ops.addToTable(value, sn, name, this.inventory.theList);
            inventoryTable.getItems().add(item);

            values.add(Double.parseDouble(value));

            System.out.println(values);

            // call formatTableView to format the currency correctly
            formatTableview();

            refreshEvent();

            //setTotalTF(totalTF, totalField());
            setTotalCount(itemCount, setTotalItemCount());

        }

        System.out.println(inventory.getTheList() + " after add");
        //inventory.sortList(inventoryTable);
        System.out.println(inventory.getTheList() + " hopefully sorted list");
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
            setTotalTF(totalTF, totalField());
            setTotalCount(itemCount, setTotalItemCount());

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
        if(validate.catchInvalidSerial(itemStringCellEditEvent.getNewValue()) && validate.validateSerialNumber(itemStringCellEditEvent.getNewValue(), false, this.inventory.getTheList(), serialNumberImage)) {
            // set it to item.setSerialNumber
            item.setSerialNumber(itemStringCellEditEvent.getNewValue().toUpperCase());
            serialNumberImage.setVisible(false);
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
        if(validate.validateString(itemStringCellEditEvent.getNewValue())) {
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
        output.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML file", "*.html"));
        output.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT file", "*.txt"));
        // show open dialog
        File file = output.showOpenDialog(null);
        if (file != null && file.getAbsolutePath().endsWith(".html")) {
            fileManagement.HTMLtoList(file.getAbsolutePath(), inventory.getTheList());
            inventoryTable.getItems().addAll(inventory.getTheList());
            formatTableview();
            inventoryTable.refresh();
        } else if(file != null && file.getAbsolutePath().endsWith(".txt")) {
            fileManagement.TXTtoList(file.getAbsolutePath(), inventory.getTheList());
            inventoryTable.getItems().addAll(inventory.getTheList());
            formatTableview();
            inventoryTable.refresh();
            System.out.println(inventory.getTheList() + "after import");
        }
        setTotalCount(itemCount, setTotalItemCount());
        setTotalCount(totalTF, totalField());
    }
}
