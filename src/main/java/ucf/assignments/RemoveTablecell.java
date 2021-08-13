/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class RemoveTablecell extends TableCell <Item, Boolean> {
    PersonalInventoryController controller = new PersonalInventoryController();
    TableOperations ops = new TableOperations();
    final JFXButton removeButton = new JFXButton("");

    RemoveTablecell(TableView inventoryTable, Inventory myInventory, TextField totaltf, String total, TextField itemCount, boolean mode) {
        setRemoveGraphic(mode);
        removeButton.setOnAction(r -> {
            Item item = getTableRow().getItem();

            double oop = Double.parseDouble(item.getValue());

            inventoryTable.getItems().remove(item);
            inventoryTable.refresh();
            ops.removeItem(item, myInventory.theList);


            int newItemCount = myInventory.theList.size();
            String newItemString;

            if(newItemCount == 1) {
                newItemString = "1 item";
            } else {
                newItemString = String.format("%d items", newItemCount);
            }

            controller.setTotalCount(itemCount, newItemString);
            double newTotal = Double.parseDouble(total.replaceAll("[$,]","")) - oop;
            DecimalFormat formatter = new DecimalFormat("$#,##0.00");
            controller.setTotalTF(totaltf, formatter.format(newTotal));
        });
    }

    @Override
    public void updateItem(Boolean r, boolean empty) {
        super.updateItem(r, empty);
        if(!empty){
            setGraphic(removeButton);
        }
    }

    public void setRemoveGraphic(boolean mode) {
        try {
            if(mode){
                removeButton.setGraphic(new ImageView((new Image(new FileInputStream("images/light/cancel.png")))));
            } else if(!mode){
                removeButton.setGraphic(new ImageView((new Image(new FileInputStream("images/dark/cancel.png")))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}