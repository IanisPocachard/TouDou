package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import modele.TachePrimaire;

public class ControlAddArchive implements EventHandler<ActionEvent> {

    private Modele modele;
    private TreeView<Tache> treeView;

    public ControlAddArchive(Modele modele, TreeView<Tache> treeView) {
        this.modele = modele;
        this.treeView = treeView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}