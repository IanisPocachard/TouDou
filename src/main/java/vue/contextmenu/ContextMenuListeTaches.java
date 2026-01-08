package vue.contextmenu;

import controleur.ControlSuppTache;
import controller.ControlAddArchive;
import controller.ControlFormAddDependance;
import controller.ControlFormAddSousTache;
import controller.ControlFormAddTache;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import modele.Modele;
import modele.Tache;
import vue.VueListe;

public class ContextMenuListeTaches extends ContextMenu {

    public ContextMenuListeTaches(Modele modele, VueListe vueListe) {
        MenuItem itemAjouterTache = new MenuItem("Ajouter une tâche");
        MenuItem itemAjouterSousTache = new MenuItem("Ajouter une sous-tâche");
        MenuItem itemAjouterDependance = new MenuItem("Ajouter une dépendance");
        MenuItem itemArchiver = new MenuItem("Archiver la tâche");
        MenuItem itemSupprimerTache = new MenuItem("Supprimer la tâche");

        itemAjouterTache.setOnAction(new ControlFormAddTache(modele));
        itemAjouterSousTache.setOnAction(new ControlFormAddSousTache(modele, vueListe));
        itemAjouterDependance.setOnAction(new ControlFormAddDependance(modele, vueListe));
        itemArchiver.setOnAction(new ControlAddArchive(modele, vueListe));
        itemSupprimerTache.setOnAction(new ControlSuppTache(modele, vueListe));

        String styleItem = """
            -fx-padding: 8 16;
            -fx-font-size: 13px;
            -fx-text-fill: #374151;
        """;

        itemAjouterTache.setStyle(styleItem);
        itemAjouterSousTache.setStyle(styleItem);
        itemAjouterDependance.setStyle(styleItem);
        itemArchiver.setStyle(styleItem);
        itemSupprimerTache.setStyle(styleItem);

        this.getItems().addAll(itemAjouterTache, itemAjouterSousTache, itemAjouterDependance, itemArchiver, itemSupprimerTache);

        this.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: #e5e7eb;
            -fx-border-width: 1;
            -fx-padding: 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0.2, 0, 4);
        """);


    }

}
