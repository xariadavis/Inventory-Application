/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

// adapted from EditingCell class in https://gist.github.com/haisi/0a82e17daf586c9bab52
// helps add listener (prevent user from entering non numerical values) for Value textfield when editing the Inventory Table

class EditingCell extends TableCell<Item, String> {

    private TextField textField;
    boolean mode;

    public EditingCell(boolean mode) {
        this.mode = mode;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setGraphic(textField);
            textField.selectAll();
            if(mode){
                textField.setStyle("""
                    -fx-font-size: 14;
                    -fx-background-radius: 15;
                    -fx-border-radius: 15;
                    -fx-font-family: 'Segoe UI Light', Regular;
                    -fx-text-fill: white;
                    -fx-background-color:  #1f1d2c;""".indent(4));
            } else {
                textField.setStyle("""
                    -fx-font-size: 14;
                    -fx-background-radius: 15;
                    -fx-border-radius: 15;
                    -fx-font-family: 'Segoe UI Light', Regular;
                    -fx-text-fill: black;
                    -fx-background-color:  #f6faff;""".indent(4));
            }
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String val, boolean empty) {
        super.updateItem(val, empty);
        if(empty) {
            setText(val);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    public void commitEdit() {
        if(!textField.getText().equals("")) {
            commitEdit(textField.getText());
        }
    }

    private void createTextField() {
        textField = new TextField(getString().replace("$",""));
        textField.setMinWidth(175);
        textField.setOnAction((e) -> commitEdit());
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(textField.getText());
            }
        });

        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d)") || !newValue.matches(".")) {
                textField.setText(newValue.replaceAll("[^\\d(.)]", ""));
            }
        }));
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
