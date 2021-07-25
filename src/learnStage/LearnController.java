package learnStage;
import java.io.File;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class LearnController {

    private final LearnView myView;
    private final LearnModel model;
    private boolean isListImported;

    public LearnController(LearnView myView) {
        this.model = new LearnModel();
        this.myView = myView;
    }

    public void start() {

        model.addFilePath(myView.getFileNameBox());
        myView.getButton(0).setOnMouseClicked(e1 -> importExelFile());
        myView.getButton(1).setOnMouseClicked(e2 -> wrong());
        myView.getButton(2).setOnMouseClicked(e3 -> correct());
        myView.getButton(3).setOnMouseClicked(e4 -> exit());
        myView.getButton(1).setOnKeyPressed(this::keyPressed);
        myView.getButton(2).setOnKeyPressed(this::keyPressed);

        myView.getFileNameBox().setOnMouseClicked(e6 -> listFileNames());
        myView.getSpreadSheetBox().setOnMouseClicked(e7 -> selectSpreadSheet());
        myView.getComboBox().setOnMouseClicked(e8 -> setSortingAlgorithm());

        myView.getStage().show();
    }

    //import the Excel file and add the number of its sheets to the choice book
    private void importExelFile() {

        if (myView.getTextField().getText().isEmpty()) {
            myView.getController().setText("choose an exel file");
            return;
        }
        if (isWorkbookOpen(myView.getTextField().getText(), myView.getController())) {

            try {
                model.importExelFile(myView.getTextField(), myView.getController());
                isListImported = true;
            } catch (IOException e) {
                myView.getController().setText("Something went wrong");
            }
        }
    }

    // rename check will work if the file is close
    private boolean isWorkbookOpen(String fileName, Text controller) {
        String path = "C:\\Users\\jamil\\Documents\\ExcelDateien Listen\\";

        File file1 = new File(path + fileName + ".xlsx");

        if (!file1.exists()) {
            controller.setText("File doesn't exist");
            return false;
        }

        if (!file1.renameTo(file1)) {
            controller.setText("Close your Exel file!!");
            return false;
        }
        return true;
    }

    //word will be added at the end of the list
    private void wrong() {

        if (isListImported) {
            model.wrong(myView.getController());
            myView.getCounter().setText(model.counter());
        }
    }

    // words will be deleted (it's no longer required)
    private void correct() {

        if (isListImported) {
            model.correct(myView.getController());
            myView.getCounter().setText(model.counter());
        }
    }

    private void exit() {
        model.openMainMenu();
        myView.getStage().close();
    }

    //First of all, click on a button, then follow the steps
//1x ENTER or 1x SPACE + ENTER -> correct = Word will be deleted
//1x ENTER or 1x SPACE + SPACE -> word will be added to the end of the list
    private void keyPressed(KeyEvent event) {

        if (event.getCode().equals(KeyCode.ENTER)) {
            correct();
        }
        if (event.getCode().equals(KeyCode.SPACE)) {
            wrong();
        }
    }

    private void listFileNames() {
        //select all files in the folder
        myView.getController().setText("import a list");
        myView.getFileNameBox().getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldNumber, newNumber) -> {
            String chooseOptions = myView.getFileNameBox().getItems().get((int) newNumber);
            myView.getTextField().setText(chooseOptions);
        });
    }

    private void selectSpreadSheet() {
        if (isListImported) {
            String displayedText = model.setSpreadSheetList(myView.getSpreadSheetBox());
            myView.getController().setText(displayedText);
        } else {
            myView.getController().setText("import a list");
        }
    }

    private void setSortingAlgorithm() {

        if (isListImported) {
            myView.getComboBox().getSelectionModel().selectedIndexProperty().addListener(
                    (observable, oldNumber, newNumber) -> {

                myView.getController().setText("");
                String menuPoint = myView.getComboBox().getItems().get((int) newNumber);
                int SheetListIndex = myView.getSpreadSheetBox().getSelectionModel().getSelectedIndex() - 1;
                String displayedText = model.shuffleWords(SheetListIndex, menuPoint, myView.getCounter());
                myView.getController().setText(displayedText);
            });
            return;
        }

        myView.getController().setText("Import a List and choose a spreadsheet");

    }
}
