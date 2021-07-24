/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RemoveTablecell extends TableCell <Item, Boolean> {
    PersonalInventoryController controller = new PersonalInventoryController();
    TableOperations ops = new TableOperations();
    final JFXButton removeButton = new JFXButton("");

    RemoveTablecell(TableView inventoryTable, ArrayList<Item> theList, TextField totaltf, String total, TextField itemCount){
        setRemoveGraphic();
        removeButton.setOnAction(r -> {
            inventoryTable.refresh();
            int index = getTableRow().getIndex();
            double oop = Double.parseDouble(theList.get(index).getValue());
            ops.removeItem(index, theList);
            System.out.println("in remove "+theList);
            inventoryTable.getItems().remove(index);
            inventoryTable.refresh();

            int newItemCount = theList.size() - 1;
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
