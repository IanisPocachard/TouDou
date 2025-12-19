package vue;

import controller.DragDropController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.Modele;
import modele.Sujet;
import modele.Tache;
import modele.TachePrimaire;

import java.util.ArrayList;
import java.util.List;

public class VueBureau extends HBox implements Observateur {

    private Modele modele;
    private DragDropController dragController;

    private VBox colToDo;
    private VBox colInProgress;
    private VBox colTesting;
    private VBox colDone;

    private List<Label> cartes = new ArrayList<>();

    public VueBureau(Modele modele) {
        this.modele = modele;
        this.modele.ajouterObservateur(this);

        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        initialiserColonnes();

        this.dragController = new DragDropController(modele);
        activerDragDropDesColonnes();

        this.actualiser(modele);
    }

    private void initialiserColonnes() {
        colToDo = creerColonne("TO DO", "#FFC108");
        colInProgress = creerColonne("IN PROGRESS", "#FF5A08");
        colTesting = creerColonne("TESTING", "#24739B");
        colDone = creerColonne("DONE", "#36CBC1");
        this.getChildren().addAll(colToDo, colInProgress, colTesting, colDone);
    }

    private void activerDragDropDesColonnes() {
        dragController.makeDroppable(colToDo, TachePrimaire.A_FAIRE);
        dragController.makeDroppable(colInProgress, TachePrimaire.EN_COURS);
        dragController.makeDroppable(colTesting, TachePrimaire.A_TESTER);
        dragController.makeDroppable(colDone, TachePrimaire.VALIDEE);
    }

    private VBox creerColonne(String titre, String couleurHex) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: " + couleurHex + "; -fx-background-radius: 10;");
        box.setMinWidth(200);
        box.setPrefWidth(250);

        Label lbl = new Label(titre);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lbl.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(lbl);
        return box;
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Modele) {
            Platform.runLater(() -> rafraichirTaches((Modele) s));
        }
    }

    private void rafraichirTaches(Modele m) {
        viderContenuColonne(colToDo);
        viderContenuColonne(colInProgress);
        viderContenuColonne(colTesting);
        viderContenuColonne(colDone);

        for (Tache t : m.getTaches()) {
            if (t instanceof TachePrimaire) {
                TachePrimaire tp = (TachePrimaire) t;

                Label carte = cartes.stream()
                        .filter(l -> l.getUserData() == tp)
                        .findFirst()
                        .orElse(null);

                if (carte == null) {
                    carte = creerCarteTache(tp);
                }


                if (carte.getParent() instanceof Pane) {
                    ((Pane) carte.getParent()).getChildren().remove(carte);
                }

                switch (tp.getEtat()) {
                    case TachePrimaire.A_FAIRE -> colToDo.getChildren().add(carte);
                    case TachePrimaire.EN_COURS -> colInProgress.getChildren().add(carte);
                    case TachePrimaire.A_TESTER -> colTesting.getChildren().add(carte);
                    case TachePrimaire.VALIDEE -> colDone.getChildren().add(carte);
                }
            }
        }
    }

    private Label creerCarteTache(TachePrimaire t) {
        Label lbl = new Label(t.getNom());
        lbl.setPadding(new Insets(10));
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setWrapText(true);

        lbl.setUserData(t);

        lbl.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 2, 0, 0, 1);");

        dragController.makeDraggable(lbl);

        cartes.add(lbl);
        return lbl;
    }

    private void viderContenuColonne(VBox box) {
        if (box.getChildren().size() > 1) {
            box.getChildren().remove(1, box.getChildren().size());
        }
    }

    // Getters
    public VBox getColToDo() { return colToDo; }
    public VBox getColInProgress() { return colInProgress; }
    public VBox getColTesting() { return colTesting; }
    public VBox getColDone() { return colDone; }
}