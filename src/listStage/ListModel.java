package listStage;

import java.io.IOException;
import java.util.Iterator;

import sample.Main;
import exel.ExportList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListModel {

    private final ObservableList<String> listDe = FXCollections.observableArrayList();
    private final ObservableList<String> listFr = FXCollections.observableArrayList();

    public ListModel() { }
    public ObservableList<String> getListDe(){ return listDe; }
    public ObservableList<String> getListFr(){ return listFr; }
    public boolean emptyList() { return listDe.isEmpty() || listFr.isEmpty(); }


    public void setNewListItem(TextField textFieldDe, TextField textFieldFr) {
        String newWordDe = listDe.size() + ". " + textFieldDe.getText();
        String newWordFr = listFr.size() + ". " + textFieldFr.getText();
        listDe.add(newWordDe);
        listFr.add(newWordFr);

        clearTxtflds(textFieldDe, textFieldFr);
        textFieldFr.requestFocus();
    }

    private void clearTxtflds(TextField textField1, TextField textField2) {
        textField1.clear();
        textField2.clear();
    }

    public void createExelFile(String fileName, String tableName, boolean fileExist) {

        ExportList exportList = new ExportList();

        for (String wordDeCopy : listDe) {
            exportList.setWordDe(wordDeCopy);
        }
        for (String wordFrCopy : listFr) {
            exportList.setWordFr(wordFrCopy);
        }
    try{
        if(fileExist){
            exportList.upgradeData(tableName, fileName);
        }else{
            exportList.exportData(tableName, fileName);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
            deleteListCompletly();
    }
    public void deleteListCompletly(){

        listDe.clear();
        listFr.clear();
    }

    public void deleteSpecificItems(TextField wordDe, TextField wordFr){

        int i = 0;
        Iterator<String> it = listDe.iterator();
        Iterator<String> it2 = listFr.iterator();

        while (it.hasNext() && it2.hasNext()) {

            String indexWordDe = i + ". " + wordDe.getText();
            String indexWordFr = i + ". " + wordFr.getText();
            String value = it.next();
            String value2 = it2.next();

            if (indexWordDe.equals(value2) && indexWordFr.equals(value)) {
                it.remove();
                it2.remove();
                clearTxtflds(wordDe, wordFr);
                break;
            }
            i++;
        }
    }
    public void openMainMenu() {
        Stage primaryStage = new Stage();
        Main newStage = new Main();
        newStage.start(primaryStage);
    }
}
