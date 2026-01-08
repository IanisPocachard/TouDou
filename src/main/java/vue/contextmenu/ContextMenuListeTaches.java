package vue.contextmenu;

import controller.ControlFormAddSousTache;
import controller.ControlFormAddTache;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import modele.Modele;
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

        this.getItems().addAll(itemAjouterTache, itemAjouterSousTache, itemAjouterDependance, itemArchiver, itemSupprimerTache);
    }

}
