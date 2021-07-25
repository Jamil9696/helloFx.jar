package learnStage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import sample.Main;
import exel.ImportList;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LearnModel {

    private final Text showWords;
    private final ImportList importList;
    private final File folder = new File("C:\\Users\\Jamil\\Documents\\ExcelDateien Listen");
    private ArrayList<String> listDe;
    private ArrayList<String> listFr;

    public LearnModel() {

        showWords = new Text("");
        importList = new ImportList();
        listDe = new ArrayList<>();
        listFr = new ArrayList<>();
    }
    public void addFilePath(ChoiceBox<String> fileListBox) {

        String[] pathNames = folder.list();
        if(pathNames == null) return;

        for (String string : pathNames) {
            if (!string.contains("~$")) {
                String fileName = string.substring(0, string.length() - 5);
                fileListBox.getItems().add(fileName);
            }
        }
    }

    public void importExelFile(TextField fileNameTxtFld,Text displayedText) throws IOException {

        String nameFile = fileNameTxtFld.getText();
        importList.addListToChoiceBox(nameFile, displayedText);

    }

    public String setSpreadSheetList(ChoiceBox<String> spreadSheetBox) {

        spreadSheetBox.setItems(FXCollections.observableArrayList("Spreadsheets"));
        importList.updateSpreadSheetChoiceBox(spreadSheetBox);

        return "choose and shuffle the Spreadsheet ";

    }
    public String shuffleWords(int sheetListIndex,String menuPoint,Text countWords) {

        importList.searchSpecificSheet(sheetListIndex);
        listDe = importList.getListDe();
        listFr = importList.getListFr();

        Random random = new Random();
        int number = random.nextInt(100);
        switch(menuPoint) {
            case "Algorithms":
                break;
            case "Random ":
                showWords.setText(sortingAlgorithms(number));
                break;
            case "Random 1":
                showWords.setText(sortingAlgorithms(1));
                break;
            case "Random 2" :
                showWords.setText(sortingAlgorithms(2));
                break;
            case "Random 3":
                showWords.setText(sortingAlgorithms(3));
                break;
        }

        String startLearning = showWords.getText();
        countWords.setText(counter());
        return startLearning;
    }

    public String counter() {
        return "only "+ listDe.size() + " words!";
    }

    public void correct(Text displayedText) {

        String string = displayedText.getText();
        if(listDe.contains(string)) {
            switchToForeignLanguage(string, displayedText);
        }

        if (listFr.contains(string)) {
            int index = listFr.indexOf(string);
            listDe.remove(index);
            listFr.remove(index);

            if(listFr.isEmpty()) {
                displayedText.setText("Félicitation, No Words");

            }else {
                String newWord = listDe.get(0);
                displayedText.setText(newWord);
            }
        }
    }
    // add the words to the end of the list
    public void wrong(Text displayedText) {

        String string = displayedText.getText();
        if(listDe.contains(string)) {
            switchToForeignLanguage(string, displayedText);
        }

        if (listFr.contains(string)) {
            int index = listFr.indexOf(string);
            int k = listDe.size();

            String oldWordDe = listDe.get(index);
            listFr.add(k, string);
            listDe.add(k, oldWordDe);
            listDe.remove(index);
            listFr.remove(index);

            displayedText.setText(listDe.get(0));
        }
    }

    private void switchToForeignLanguage(String germanWord, Text displayedText) {
        int index = listDe.indexOf(germanWord);
        String translatedWord = listFr.get(index);
        displayedText.setText(translatedWord);
    }

    // your brain will also learn in which order the words will be asked
    // try to vary the random functions
    private String sortingAlgorithms(int index) {
        if (!listDe.isEmpty() || !listFr.isEmpty()) {
            Collections.shuffle(listDe, new Random(index));
            Collections.shuffle(listFr, new Random(index));

            return listDe.get(0);
        }
        return "select a sorting algorithm";

    }

    public void openMainMenu() {
        Main object = new Main();
        Stage primaryStage = new Stage();
        object.start(primaryStage);

    }
}
