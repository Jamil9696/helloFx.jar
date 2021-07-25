package sample;
import java.io.File;
import java.util.Objects;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import learnStage.LearnController;
import learnStage.LearnModel;
import learnStage.LearnView;
import listStage.ListController;
import listStage.ListMyView;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        try {

            HBox hBox = new HBox();
            Button learn = new Button("learn");
            Button modify = new Button("modify");

            hBox.getChildren().addAll(learn, modify);
            checkFolder();

            hBox.getStyleClass().add("hbox");
            Scene scene = new Scene(hBox, 400, 300);

            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Bienvenue! Welcome!");
            primaryStage.show();

            EventHandler<MouseEvent> newList = ev1 -> {

                ListMyView myView = new ListMyView();
                ListController controller = new ListController(myView);
                controller.start();
                primaryStage.close();
            };
            modify.addEventFilter(MouseEvent.MOUSE_CLICKED, newList);

            EventHandler<MouseEvent> learnWords = ev2 ->{
                    LearnView myView = new LearnView();
                    LearnController learnStage = new LearnController(myView);
                    learnStage.start();
                    primaryStage.close();
            };

            learn.addEventFilter(MouseEvent.MOUSE_CLICKED, learnWords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkFolder() {
        File newDir = new File("C:\\Users\\Jamil\\Documents\\ExcelDateien Listen\\");
        if (!newDir.exists()) newDir.mkdirs();

    }
}