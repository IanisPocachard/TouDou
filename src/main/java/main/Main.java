package main;

import controller.DragDropController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;
import vue.VueBureau;
import vue.VueFormGantt;
import vue.VueHistorique;
import vue.VueListe;

import java.util.HashMap;
import java.util.Map;

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

        // Style des boutons
        String styleBouton = """
            -fx-background-color: #3b82f6;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 8 16 8 16;
            -fx-background-radius: 8;
        """;

        btnListeTaches.setStyle(styleBouton);
        btnKanban.setStyle(styleBouton);
        btnDiagGantt.setStyle(styleBouton);
        btnTachesArchive.setStyle(styleBouton);

        boutons.setSpacing(10);
        boutons.setStyle("-fx-padding: 10;");


        boutons.getChildren().addAll(btnListeTaches, btnKanban, btnDiagGantt, btnTachesArchive);


        // LISTE DES TACHES
        VueListe liste = new VueListe(modele);


        // TABLEAU
        VueBureau kanban = new VueBureau(modele);

        Map<Label, TachePrimaire> carteMap = new HashMap<>();
        for (Tache t : modele.getTaches()) {
            if (t instanceof TachePrimaire) {
                TachePrimaire tp = (TachePrimaire) t;
                Label carte = kanban.getCartes().stream()
                        .filter(l -> l.getText().equals(tp.getNom()))
                        .findFirst().orElse(null);
                if (carte != null) {
                    carteMap.put(carte, tp);
                }
            }
        }

        DragDropController controller = new DragDropController(modele, carteMap);
        for (Label carte : carteMap.keySet()) controller.makeDraggable(carte);
        controller.makeDroppable(kanban.getColToDo(), TachePrimaire.A_FAIRE);
        controller.makeDroppable(kanban.getColInProgress(), TachePrimaire.EN_COURS);
        controller.makeDroppable(kanban.getColTesting(), TachePrimaire.A_TESTER);
        controller.makeDroppable(kanban.getColDone(), TachePrimaire.VALIDEE);

        // HISTORIQUE
        VBox historique = new VBox();
        Label labelHistorique = new Label("Historique : ");
        VueHistorique vueHistorique = new VueHistorique(modele);
        historique.getChildren().addAll(labelHistorique, vueHistorique);


        // AJOUT DE TOUS LES ELEMENTS
        HBox elements = new HBox(20);
        VBox partieGauche = new VBox();
        VBox partieDroite = new VBox();

        partieGauche.getChildren().addAll(liste);
        partieDroite.getChildren().addAll(kanban, historique);
        elements.getChildren().addAll(partieGauche, partieDroite);

        screen.getChildren().addAll(boutons, elements);

        Scene scene = new Scene(screen);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TouDou - Tableau de bord");
        primaryStage.show();
    }
}
