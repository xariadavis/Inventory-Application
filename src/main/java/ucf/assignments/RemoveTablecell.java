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

public class RemoveTablecell extends TableCell <Item, Boolean> {
    PersonalInventoryController controller = new PersonalInventoryController();
    TableOperations ops = new TableOperations();
    final JFXButton removeButton = new JFXButton("");

    RemoveTablecell(TableView inventoryTable, Inventory myInventory, TextField totaltf, String total, TextField itemCount) {
        setRemoveGraphic();
        removeButton.setOnAction(r -> {
            inventoryTable.refresh();
            int index = getTableRow().getIndex();

            double oop = Double.parseDouble(myInventory.theList.get(index).getValue());
            inventoryTable.getItems().remove(index);
            ops.removeItem(index, myInventory.theList);
            inventoryTable.refresh();

            int newItemCount = myInventory.theList.size();
            String newItemString;

            if(newItemCount == 1) {
                newItemString = "1 item";
            } else {
                newItemString = String.format("%d items", newItemCount);
            }

            controller.setTotalCount(itemCount, newItemString);
            double newTotal = Double.parseDouble(total.replace("$","")) - oop;
            String string = String.format("$%.2f", newTotal);
            controller.setTotalTF(totaltf, String.valueOf(string));
            System.out.println();
        });
    }

    @Override
    public void updateItem(Boolean r, boolean empty) {
        super.updateItem(r, empty);
        if(!empty){
            setGraphic(removeButton);
        }
    }

    public void setRemoveGraphic() {
        try {
            removeButton.setGraphic(new ImageView((new Image(new FileInputStream("images/cancel.png")))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}