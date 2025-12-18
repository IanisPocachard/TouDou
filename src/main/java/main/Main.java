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

        // LIGNE DE BOUTONS
        HBox boutons = new HBox();
        Button btnListeTaches = new Button("Liste des tâches");
        Button btnKanban = new Button("Vue Kanban");
        Button btnDiagGantt = new Button("Diagramme de Gantt");
        btnDiagGantt.setOnAction(e -> VueFormGantt.afficher(modele));
        Button btnTachesArchive = new Button("Tâches archivées");
        boutons.getChildren().addAll(btnListeTaches, btnKanban, btnDiagGantt, btnTachesArchive);


        // LISTE DES TACHES
        VueListe liste = new VueListe(modele);


        // TABLEAU
        VueBureau kanban = new VueBureau(modele);


        // HISTORIQUE
        VBox historique = new VBox();
        Label labelHistorique = new Label("Historique : ");
        VueHistorique vueHistorique = new VueHistorique(modele);
        historique.getChildren().addAll(labelHistorique, vueHistorique);


        // AJOUT DE TOUS LES ELEMENTS
        screen.getChildren().addAll(boutons, liste, kanban, historique);

        Scene scene = new Scene(screen);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TouDou - Tableau de bord");
        primaryStage.show();
    }
}
