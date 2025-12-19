package controller;

import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import modele.Modele;
import modele.TachePrimaire;

public class DragDropController {

    private Modele modele;

    public DragDropController(Modele modele) {
        this.modele = modele;
    }

    // Rend une carte déplaçable
    public void makeDraggable(Label carte) {
        carte.setOnDragDetected(event -> {
            Dragboard db = carte.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(carte.getText());
            db.setContent(content);

            event.consume();
        });
    }

    // Rend une colonne capable de recevoir une carte
    public void makeDroppable(VBox colonne, int etatCible) {
        colonne.setOnDragOver(event -> {
            if (event.getGestureSource() != colonne && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        colonne.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                if (event.getGestureSource() instanceof Label) {
                    Label carteDeplacee = (Label) event.getGestureSource();

                    if (carteDeplacee.getUserData() instanceof TachePrimaire) {
                        TachePrimaire tache = (TachePrimaire) carteDeplacee.getUserData();

                        modele.changerEtat(tache.getId(), etatCible);
                        success = true;
                    }
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }
}