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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private double getValue() {
        return Double.parseDouble(valueTF.getText());
    }

    private void formatTableview() {

        // format value column
        DecimalFormat currency = new DecimalFormat("$0.00");
        valueColumn.setCellValueFactory(cellData -> {
            String formattedCost = currency.format(getValue());
            return new SimpleStringProperty(formattedCost);
        });
    }

    public void addItemToTable(ActionEvent actionEvent) {
        String value = valueTF.getText();
        String sn = snTF.getText();
        String name = nameTF.getText();

        Item item = ops.addToTable(Double.parseDouble(value), sn, name, this.inventory.theList);
        formatTableview();

        inventoryTable.getItems().add(item);
    }
}
