/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class RemoveTablecell extends TableCell <Item, Boolean> {
    final JFXButton removeButton = new JFXButton("");

    RemoveTablecell(final TableView inventoryTable, ArrayList<Item> theList, ArrayList<Double> values){
        setRemoveGraphic();
        removeButton.setOnAction(r -> {
            inventoryTable.refresh();
            int index = getTableRow().getIndex();
            theList.remove(index);
            inventoryTable.getItems().remove(index);
            //values.remove(index);
            inventoryTable.refresh();
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
