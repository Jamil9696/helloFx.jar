package learnStage;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class LearnView {

    private final Stage stage;
    private final Text controller,countWords;
    private final Button[] btnList;
    private final TextField inputFileName;
    private final ChoiceBox<String> fileNameBox;
    private final ChoiceBox<String> spreadSheetBox;
    private final ComboBox<String> sortingAlgorithmBox;

    public LearnView() {
        stage = new Stage();
        controller = new Text("choose and import a list");
        countWords = new Text("");
        btnList = new Button[4];
        inputFileName = new TextField();
        fileNameBox = new ChoiceBox<>();
        spreadSheetBox = new ChoiceBox<>();
        sortingAlgorithmBox = new ComboBox<>();

        setView();
    }

    public void setView() {

        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 500);
        inputFileName.setPromptText("Choose And import a list");
        controller.getStyleClass().add("showText");
        countWords.getStyleClass().add("showText");

        String[] name_btn_list = {"import list","wrong","right","exit"};

        // fill and style FX-components
        setLayoutButton(name_btn_list);
        setLayoutCheckBox("Algorithms","Random ","Random 1", "Random 2", "Random 3");
        setLayoutChoiceBox(fileNameBox, "Number of Lists", "choose a List","choice box");
        setLayoutChoiceBox(spreadSheetBox, "Spreadsheets", "which section","choice box");

        root.getStyleClass().add("vbox");
        root.getChildren().addAll(setLayoutHBox());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet3.css")).toExternalForm());
        stage.setScene(scene);

    }
    // Navbar
    private HBox[] setLayoutHBox(){
        HBox[] hBoxList = {
                new HBox(btnList[0],inputFileName, fileNameBox,spreadSheetBox,sortingAlgorithmBox,btnList[3]),
                new HBox(new VBox(controller,countWords)),
                new HBox(btnList[1],btnList[2])
        };
        for (int i = 0; i < hBoxList.length; i++) {
            hBoxList[i].getStyleClass().add("hbox" + (i + 1));
        }
        hBoxList[1].getChildren().get(0).getStyleClass().add("vboxCenter");

        return hBoxList;
    }

    private void setLayoutCheckBox(String title, String item1, String item2, String item3, String item4){
        sortingAlgorithmBox.setItems(FXCollections.observableArrayList(title, item1,item2,item3,item4));
        sortingAlgorithmBox.setPromptText(title);
        sortingAlgorithmBox.getStyleClass().add("combo box");
    }
    private void setLayoutButton(String[] name_btn_list){

        for (int i = 0; i < name_btn_list.length && i < btnList.length; i++) {
            Button btn = new Button(name_btn_list[i]);
            btnList[i] = btn;
        }
    }

    private void setLayoutChoiceBox(ChoiceBox<String> choiceBox, String title, String toolTip,String styleClass) {
        choiceBox.setItems(FXCollections.observableArrayList(title));
        choiceBox.getSelectionModel().select(0);
        choiceBox.setTooltip(new Tooltip(toolTip));
        choiceBox.getStyleClass().add(styleClass);
    }

    // getters
    public Button getButton(int index) {
        return btnList[index];
    }
    public TextField getTextField(){
        return inputFileName;
    }
    public ChoiceBox<String> getFileNameBox(){
        return fileNameBox;
    }
    public ChoiceBox<String> getSpreadSheetBox(){
        return spreadSheetBox;
    }
    public ComboBox<String> getComboBox() {
        return sortingAlgorithmBox;
    }
    public Stage getStage() {
        return stage;
    }
    public Text getController(){
        return controller;
    }
    public Text getCounter(){
        return countWords;
    }
}
