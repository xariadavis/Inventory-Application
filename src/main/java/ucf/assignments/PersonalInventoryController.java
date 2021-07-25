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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PersonalInventoryController {
    TableOperations ops = new TableOperations();
    String title;
    Inventory inventory = new Inventory(title);
    FileManagement fileManagement = new FileManagement();
    Sorting sorting = new Sorting();
    ArrayList<Double> values = new ArrayList<>();
    Validator validate = new Validator();

    @FXML private AnchorPane Parent;
    @FXML public TableColumn<Item, String> valueColumn = new TableColumn<>("Value");
    @FXML public TableColumn<Item, String> snColumn = new TableColumn<>("Serial Number");
    @FXML public TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
    @FXML public TableColumn<Item, Boolean> deleteColumn = new TableColumn<>("");
    @FXML private TableView<Item> inventoryTable;
    @FXML private TextField valueTF, snTF, nameTF, searchBox, totalTF, snWordCount, nameWordCount, itemCount, listTitleTF;
    @FXML ImageView serialNumberImage, nameImage;
    @FXML JFXButton clearListButton, importButton, exportButton;

    public void initialize() {

        Label placeholder = new Label();
        placeholder.setText("No items found in inventory.");
        placeholder.setTextFill(Color.valueOf("white"));
        placeholder.setOpacity(0.5);
        placeholder.setStyle("-fx-font-size: 14; -fx-font-family: 'Segoe UI Light', Regular;");
        inventoryTable.setPlaceholder(placeholder);

        handleToolTips();

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

        setTotalTF(totalTF, "$0.00");
        setTotalCount(itemCount, "0 items");

        searchTable();

        // listener for serial number textfield
        snTF.textProperty().addListener(((observable, oldValue, newValue) -> { int s = newValue.length();
            snWordCount.setText("Character Count: " + s);
            if(s == 10) {
                serialNumberImage.setVisible(true);
                snWordCount.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-family: 'Segoe UI Light', Regular;");
            } else {
                serialNumberImage.setVisible(false);
                snWordCount.setStyle("-fx-text-fill:  #6d2bf8; -fx-background-color: transparent; -fx-font-family: 'Segoe UI Light', Regular;");
            }
        }));

        // listener for name textfield
        nameTF.textProperty().addListener(((observable, oldValue, newValue) -> { int n = newValue.length();
            nameWordCount.setText("Character Count: " + n);
            if(n >= 2 && n <= 256) {
                nameImage.setVisible(true);
                nameWordCount.setStyle("-fx-text-fill: white; -fx-background-color: transparent; -fx-font-family: 'Segoe UI Light', Regular;");
            } else {
                nameImage.setVisible(false);
                nameWordCount.setStyle("-fx-text-fill:  #6d2bf8; -fx-background-color: transparent; -fx-font-family: 'Segoe UI Light', Regular;");
            }
        }));

        valueTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d)$") || !newValue.matches(".")) {
                valueTF.setText(newValue.replaceAll("[^\\d(.)$]", ""));
            }
        });
    }

    private void handleToolTips() {
        Tooltip importTip = new Tooltip("Import file");
        importTip.setShowDelay(Duration.seconds(0.05));
        importButton.setTooltip(importTip);

        Tooltip exportTip = new Tooltip("Export file");
        exportTip.setShowDelay(Duration.seconds(0.05));
        exportButton.setTooltip(exportTip);

        Tooltip clearTip = new Tooltip("Clear list");
        clearTip.setShowDelay(Duration.seconds(0.05));
        clearListButton.setTooltip(clearTip);
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
        inventoryTable.refresh();
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
        inventoryTable.refresh();
    }

    // populate total data
    public String totalField() {
        double total = inventory.getTheList().stream().mapToDouble(item -> Double.parseDouble(item.getValue())).sum();
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        return formatter.format(total);
    }

    public void setTotalTF(TextField totalTF, String total) {
        totalTF.setText(total);
    }

    public String setTotalItemCount() {
        String itemString;
        if(inventory.getTheList().size() == 1) {
            itemString = "1 item";
        } else {
            itemString = String.format("%d items", inventory.getTheList().size());
        }
        return itemString;
    }

    public void setTotalCount(TextField itemCount, String itemTotal) {
        itemCount.setText(itemTotal);
    }

    // if user only enters int, then convert to proper double format
    private void formatValueColumn() {
        // format value column
        DecimalFormat currency = new DecimalFormat("$#,##0.00");

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
            formatValueColumn();

            refreshEvent();

            setTotalTF(totalTF, totalField());
            setTotalCount(itemCount, setTotalItemCount());

        }
    }


    // validate input for edited value
    public void editItemValueInTable(TableColumn.CellEditEvent<Item, String> itemStringCellEditEvent) {

        // get item to edit
        Item item = inventoryTable.getSelectionModel().getSelectedItem();

        // set a variable to the old value -- set this if the user enters invalid input
        String oldValue = item.getValue();

        formatValueColumn();
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
            fileManagement.listToHTML(file.getAbsolutePath(), inventory.getTheList(), inventory.getTitle());
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
            clearList();
            fileManagement.HTMLtoList(file.getAbsolutePath(), inventory.getTheList());
            listTitleTF.setText(inventory.getTitle());
            inventoryTable.getItems().addAll(inventory.getTheList());
            formatValueColumn();
            inventoryTable.refresh();
        } else if(file != null && file.getAbsolutePath().endsWith(".txt")) {
            clearList();
            fileManagement.TXTtoList(file.getAbsolutePath(), inventory.getTheList());
            inventoryTable.getItems().addAll(inventory.getTheList());
            formatValueColumn();
            inventoryTable.refresh();
        }
        setTotalCount(itemCount, setTotalItemCount());
        setTotalCount(totalTF, totalField());
    }

    public void onEnterName(ActionEvent actionEvent) {
        nameTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    addItemToTable();
                    valueTF.requestFocus();
                }
            }
        });
    }

    public void saveListTitle(ActionEvent actionEvent) {
        listTitleTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    inventory.setTitle(listTitleTF.getText());

                    if(listTitleTF.getText().isBlank()) {
                        listTitleTF.setText("Inventory");
                    }

                    Parent.requestFocus();
                }
            }
        });
    }

    public void clearList() {
        inventoryTable.getItems().clear();
        // call the option class and clearList to clear the actual array list
        this.inventory.getTheList().clear();
        setTotalTF(totalTF, totalField());
        setTotalCount(itemCount, setTotalItemCount());
    }
}
