package listStage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class ListMyView{

    private final ListView<String> frenchList;
    private final ListView<String> germanList;
    private final TextField []txtFldList;
    private final Button []btnList;
    private final Text controller;
    private final Stage stage;

    public ListMyView() {

        germanList = new ListView<>();
        frenchList = new ListView<>();
        txtFldList = new TextField[4];
        btnList = new Button[5];
        controller = new Text("Click on the List or write the word");
        stage = new Stage();
        setView();

    }
    public void setView(){

        VBox root = new VBox();

        String []name_promptText_List = {"sheed name", "file name", "in french", "in german"};
        String []name_btnName_List = {"export list", "save", "delete", "delete list", "exit"};

        for (int i = 0;i < name_btnName_List.length && i < btnList.length; i++) {
            Button btn = new Button(name_btnName_List[i]);
            btnList[i] = btn;
        }

        for (int i = 0; i < name_promptText_List.length && i < txtFldList.length; i++) {
            TextField txtfld = new TextField();
            txtfld.setPromptText(name_promptText_List[i]);
            txtfld.getStyleClass().add("textfield");
            txtFldList[i] = txtfld;
        }
        HBox []hBoxList = { new HBox(btnList),new HBox(txtFldList), new HBox(), new HBox()};
        for(int i = 0; i < hBoxList.length; i++) {
            hBoxList[i].getStyleClass().add("hbox" + (i+1));
        }


        controller.getStyleClass().add("text1");
        hBoxList[0].getChildren().get(0).getStyleClass().add("first-btn");
        hBoxList[2].getChildren().add(controller);
        hBoxList[3].getChildren().addAll(new VBox(new Label("Liste Deutsch:"), germanList),
                new VBox(new Label("Liste foreign language:"),
                        frenchList));
        root.getChildren().addAll(hBoxList);
        root.getStyleClass().add("vbox");

        setListStyle(frenchList, 450, 550, "listFr");
        setListStyle(germanList, 450, 550, "listDe");
        // click on the List item in order to delete them
        Scene scene = new Scene(root, 790, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet2.css")).toExternalForm());
        stage.setMinWidth(790.0);
        stage.setScene(scene);

    }

    private void setListStyle(ListView<String> list,int width,int height,String styleClass ) {
        list.setPrefSize(width, height);
        list.getStyleClass().add(styleClass);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public ListView<String> getListViewFr() {
        return frenchList;
    }
    public ListView<String> getListViewDe() {
        return germanList;
    }

    public Button getButton(int index) {
        return btnList[index];
    }

    public TextField getTxtField(int index) {
        return txtFldList[index];

    }
    public Text getController() {
        return controller;
    }
    public Stage getStage() {
        return stage;
    }
}
