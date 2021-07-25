package listStage;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ListController{
    private final ListMyView myView;
    private final ListModel model;

    public ListController(ListMyView myView) {
        this.myView = myView;
        this.model = new ListModel();
    }

    public void start() {


        myView.getListViewDe().setItems(model.getListDe());
        myView.getListViewFr().setItems(model.getListFr());

        myView.getButton(0).setOnMouseClicked(e1 -> export());
        myView.getButton(1).setOnMousePressed(e2 -> save());
        myView.getButton(2).setOnMouseClicked(e4 -> delete());
        myView.getButton(3).setOnMouseClicked(e5 -> deleteAll());
        myView.getButton(4).setOnMousePressed(e5 -> exit());

        // click on the List item in order to delete them
        myView.getListViewDe().setOnMouseClicked(e6 -> clickOnTheList());
        myView.getListViewFr().setOnMouseClicked(e7 -> clickOnTheList());

        myView.getTxtField(2).setOnKeyPressed(this::switchTxtFields);
        myView.getTxtField(3).setOnKeyPressed(this::switchTxtFields);
        // switch from french text field to german text field and safe the new Word by pressing ENTER
        myView.getStage().show();


    }
    private boolean isEmptyTxtFields(int index1, int index2){

        return myView.getTxtField(index1).getText().isEmpty() || myView.getTxtField(index2).getText().isEmpty();
    }
    // write the word in the list and set requestFocus on french-text field
    private void switchTxtFields(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {

            if (myView.getTxtField(3).isFocused()) {
                save();
                return;
            }
            if(myView.getTxtField(2).isFocused()){
                myView.getTxtField(3).requestFocus();

            }
        }
    }
    private void save() {

            // check german and french Txtfields
        if (isEmptyTxtFields(2,3)) {
            myView.getController().setText("a word is missing");
            return;
        }

        model.setNewListItem(myView.getTxtField(3), myView.getTxtField(2));
        myView.getController().setText("new Word has been added");

    }
    // export the list to excel
    private void export() {
        if (isEmptyTxtFields(0,1)) {
            myView.getController().setText("file and table name must be included!");
            return;
        }

        checkFile();
    }

    //File has to be closed in order to modify them (rename check)
    private void checkFile() {

        final String PATH = "C:\\Users\\jamil\\Documents\\ExcelDateien Listen\\";
        String fileName = myView.getTxtField(1).getText();
        String tableName = myView.getTxtField(0).getText();
        File file1 = new File(PATH + fileName + ".xlsx");

        if (!file1.exists()) {
                Platform.runLater(() -> model.createExelFile(fileName, tableName,false));
                myView.getController().setText("List has been successfully created");
                return;
            }
            // updating an exel file while writing will probably corrupt it
        if (!file1.renameTo(file1)) {
                myView.getController().setText("close your exel file");
                return;
            }
            Platform.runLater(() -> model.createExelFile(fileName, tableName,true));
            myView.getController().setText("List has been successfully updated");
    }
    //delete the list
    private void deleteAll() {
        model.deleteListCompletly();
        myView.getController().setText("");

    }
    // delete manually
    private void delete() {

        myView.getController().setText("");

        if(isEmptyTxtFields(2,3)) {
            myView.getController().setText("select the words by clicking on the list\nor add new words");
            return;
        }
        model.deleteSpecificItems(myView.getTxtField(2),myView.getTxtField(3));
        myView.getController().setText("");
    }

    // delete Items by clicking on the lists
    private void clickOnTheList(){

        if (model.emptyList()) {
            myView.getController().setText("your lists are empty");
            return;
        }
        String stringDe = myView.getListViewDe().getSelectionModel().getSelectedItem();
        myView.getTxtField(3).setText(stringDe);

        String stringFr = myView.getListViewFr().getSelectionModel().getSelectedItem();
        myView.getTxtField(2).setText(stringFr);
        myView.getController().setText("select the words by clicking on the list");
    }

    private void exit() {

        if(!model.emptyList()) {
            myView.getController().setText("Don't forget to export your lists");
            return;
        }
        model.openMainMenu();
        myView.getStage().close();

    }
}



