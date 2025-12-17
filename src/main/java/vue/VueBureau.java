package vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modele.Modele;
import modele.Sujet;
import modele.Tache;
import modele.TachePrimaire;

import java.util.List;

public class VueBureau extends HBox implements Observateur {

    private Modele modele;

    private VBox colToDo;
    private VBox colInProgress;
    private VBox colTesting;
    private VBox colDone;

    public VueBureau(Modele modele) {
        this.modele = modele;

        this.modele.ajouterObservateur(this);

        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        initialiserColonnes();

        this.actualiser(modele);
    }

    private void initialiserColonnes() {

        colToDo = creerColonne("TO DO", "#e6e6fa");
        colInProgress = creerColonne("IN PROGRESS", "#fffacd");
        colTesting = creerColonne("TESTING", "#ffdab9");
        colDone = creerColonne("DONE", "#98fb98");
        this.getChildren().addAll(colToDo, colInProgress, colTesting, colDone);
    }

    private VBox creerColonne(String titre, String couleurHex) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));

        box.setStyle("-fx-background-color: " + couleurHex + ";" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;");

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
            rafraichirTaches((Modele) s);
        }
    }

    private void rafraichirTaches(Modele m) {
        viderContenuColonne(colToDo);
        viderContenuColonne(colInProgress);
        viderContenuColonne(colTesting);
        viderContenuColonne(colDone);

        List<Tache> taches = m.readAll();
        if (taches == null) return;

        for (Tache t : taches) {
            if (t instanceof TachePrimaire) {
                TachePrimaire tp = (TachePrimaire) t;
                Label carte = creerCarteTache(tp);

                switch (tp.getEtat()) {
                    case TachePrimaire.A_FAIRE:
                        colToDo.getChildren().add(carte);
                        break;
                    case TachePrimaire.EN_COURS:
                        colInProgress.getChildren().add(carte);
                        break;
                    case TachePrimaire.A_TESTER:
                        Node card;
                        colTesting.getChildren().add(card);
                        colTesting.getChildren().add(carte);
                        break;
                    case TachePrimaire.VALIDEE:
                        colDone.getChildren().add(carte);
                        break;
                }
            }
        }
    }

    private Label creerCarteTache(TachePrimaire t) {
        Label lbl = new Label(t.getNom());
        lbl.setPadding(new Insets(10));
        lbl.setMaxWidth(Double.MAX_VALUE);
        lbl.setWrapText(true);
        lbl.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6);" +
                "-fx-background-radius: 5;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 2, 0, 0, 1);");

        return lbl;
    }

    private void viderContenuColonne(VBox box) {
        if (box.getChildren().size() > 1) {
            box.getChildren().remove(1, box.getChildren().size());
        }
    }
}