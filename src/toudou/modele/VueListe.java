package modele;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.Date;

public class VueListe extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        TreeItem<Tache> tache0 = new TreeItem<Tache>(new TachePrimaire("Tache racine", "Tache primaire de l'arbre", 1, new Date(10/12/2025), new Date(15/12/2025)));
        TreeItem<Tache> tache1 = new TreeItem<Tache>(new SousTache("Tache 1", "Tache 1 de l'arbre", 1));

        tache0.getChildren().addAll(tache1);
        tache0.setExpanded(true);

        TreeView treeView = new TreeView<>(tache0);

        treeView.setCellFactory(new Callback<TreeView<Tache>, TreeCell<Tache>>() {
            @Override
            public TreeCell call(TreeView treeView) {
                TreeCell<Tache> cell = new TreeCell<Tache>() {
                    @Override
                    protected void updateItem(Tache tache, boolean empty) {
                        // une TreeCell peut changer de tâche, donc changer de TreeItem
                        super.updateItem(tache, empty);
                        if (tache != null) {
                            setText(tache.toString());
                            setStyle(" -fx-background-color: grey ; ");
                        } else {
                            setText("");
                            setStyle("");
                        }
                    }
                };
                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        System.out.println("click");
                    }
                });
                return cell;
            }
        });

        Scene scene = new Scene(treeView, 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
