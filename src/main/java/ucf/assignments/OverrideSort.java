package ucf.assignments;

import javafx.scene.control.TableView;

public class OverrideSort extends TableView <Item> {
    PersonalInventoryController test = new PersonalInventoryController();
    @Override
    public void sort() {
        //super.sort();
        System.out.println("Im actually in here");
        test.sortTable();
    }
}
