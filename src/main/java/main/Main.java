package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Modele;
import vue.VueBureau;
import vue.VueFormGantt;
import vue.VueHistorique;
import vue.VueListe;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox screen = new VBox();
        Modele modele = new Modele();

        // 1. BOUTONS
        HBox boutons = new HBox();

        Button btnListeTaches = new Button("Liste des tâches");
        Button btnKanban = new Button("Vue Kanban");
        Button btnDiagGantt = new Button("Diagramme de Gantt");
        btnDiagGantt.setOnAction(e -> VueFormGantt.afficher(modele));
        Button btnTachesArchive = new Button("Tâches archivées");
        boutons.getChildren().addAll(btnListeTaches, btnKanban, btnDiagGantt, btnTachesArchive);

        VueListe liste = new VueListe(modele);
        VueBureau kanban = new VueBureau(modele);

        VBox historique = new VBox();
        historique.getChildren().addAll(new Label("Historique : "), new VueHistorique(modele));

        screen.getChildren().addAll(boutons, liste, kanban, historique);

        Scene scene = new Scene(screen, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TouDou - Tableau de bord");
        primaryStage.show();
    }
}