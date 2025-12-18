package controller;

import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import modele.Modele;
import modele.TachePrimaire;

import java.util.Map;

public class DragDropController {

    private Modele modele;
    private Map<Label, TachePrimaire> carteMap; // permet de retrouver la tâche associée à chaque Label

    public DragDropController(Modele modele, Map<Label, TachePrimaire> carteMap) {
        this.modele = modele;
        this.carteMap = carteMap;
    }

    // rend une carte draggable
    public void makeDraggable(Label carte) {
        TachePrimaire tache = carteMap.get(carte);

        carte.setOnDragDetected(event -> {
            Dragboard db = carte.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            // on met l'ID de la tâche dans le dragboard
            content.putString(String.valueOf(tache.getId()));
            db.setContent(content);
            event.consume();
        });
    }

    // rend une colonne droppable
    public void makeDroppable(VBox colonne, int etatCible) {
        colonne.setOnDragOver(event -> {
            if (event.getGestureSource() != colonne && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        colonne.setOnDragEntered(event -> {
            event.consume();
        });

        colonne.setOnDragExited(event -> {
            event.consume();
        });

        colonne.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                int idTache = Integer.parseInt(db.getString());
                modele.changerEtat(idTache, etatCible); // mise à jour du modèle
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
