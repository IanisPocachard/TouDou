import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox screen = new HBox();

        Scene scene = new Scene(screen);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TouDou - Tableau de bord");
        primaryStage.show();
    }
}
