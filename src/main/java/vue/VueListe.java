package vue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modele.Sujet;
import modele.Tache;
import modele.TachePrimaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VueListe extends BorderPane implements Observateur {

    private final Sujet sujet;

    private final TreeItem<Tache> root = new TreeItem<>(null);
    private final TreeView<Tache> treeView = new TreeView<>(root);

    private final ObservableList<TachePrimaire> tachesPrimaires = FXCollections.observableArrayList();
    private final Map<TachePrimaire, TreeItem<Tache>> itemByPrimaire = new HashMap<>();

    private ComboBox<TachePrimaire> cbParent;

    // Champs UI
    private TextField nomP, descP, dureeP;
    private DatePicker debut, echeance;

    private TextField nomS, descS, dureeS;

    public VueListe(Sujet sujet) {
        this.sujet = sujet;

        // s'abonner
        this.sujet.ajouterObservateur(this);

        buildUI();

        // au départ tout est vide => on affiche ce que le sujet a (souvent rien)
        actualiser();
    }

    private void buildUI() {

        // ---- TreeView ----
        root.setExpanded(true);
        treeView.setShowRoot(false);

        // IMPORTANT : ne pas afficher toString()
        treeView.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(Tache tache, boolean empty) {
                super.updateItem(tache, empty);
                if (empty || tache == null) setText(null);
                else setText(tache.getNom());
            }
        });

        // ----------------- BARRE AJOUT TACHE -----------------
        nomP = new TextField();
        nomP.setPromptText("Nom tâche");

        descP = new TextField();
        descP.setPromptText("Description");

        dureeP = new TextField();
        dureeP.setPromptText("Durée");

        debut = new DatePicker(LocalDate.now());
        echeance = new DatePicker(LocalDate.now().plusDays(1));

        Button addTache = new Button("Ajouter tâche");
        addTache.setOnAction(e -> ajouterTachePrimaire());

        HBox barTache = new HBox(8,
                new Label("Tâche:"),
                nomP, descP, dureeP, debut, echeance, addTache
        );
        barTache.setPadding(new Insets(10));

        // ----------------- BARRE AJOUT SOUS-TACHE -----------------
        cbParent = new ComboBox<>(tachesPrimaires);
        cbParent.setPromptText("Parent");

        // combo = nom uniquement
        cbParent.setCellFactory(list -> new ListCell<>() {
            @Override protected void updateItem(TachePrimaire item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });
        cbParent.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(TachePrimaire item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNom());
            }
        });

        nomS = new TextField();
        nomS.setPromptText("Nom sous-tâche");

        descS = new TextField();
        descS.setPromptText("Description");

        dureeS = new TextField();
        dureeS.setPromptText("Durée");

        Button addSous = new Button("Ajouter sous-tâche");
        addSous.setOnAction(e -> ajouterSousTache());

        HBox barSous = new HBox(8,
                new Label("Sous-tâche:"),
                cbParent, nomS, descS, dureeS, addSous
        );
        barSous.setPadding(new Insets(10));

        // ----------------- LAYOUT (VueListe est un BorderPane) -----------------
        this.setTop(barTache);
        this.setCenter(treeView);
        this.setBottom(barSous);
    }

    // ====== OBSERVATEUR ======
    @Override
    public void actualiser() {
        // L’idée : reconstruire la TreeView depuis le sujet
        // ⚠️ adapte le nom de la méthode du sujet selon ton code :
        // ex: ((TacheManager)sujet).getTachesRacines()

        List<TachePrimaire> racines = sujet.getTachesPrimaires(); // <-- adapte si besoin

        // reset
        root.getChildren().clear();
        itemByPrimaire.clear();
        tachesPrimaires.clear();

        if (racines == null) return;

        // recharge
        for (TachePrimaire tp : racines) {
            tachesPrimaires.add(tp);

            TreeItem<Tache> item = new TreeItem<>(tp);
            item.setExpanded(true);
            root.getChildren().add(item);

            itemByPrimaire.put(tp, item);

            // enfants (si tu stockes tes sous-tâches dans dependances)
            for (Tache enfant : tp.getDependances()) { // <-- adapte si getSousTaches()
                item.getChildren().add(new TreeItem<>(enfant));
            }
        }
    }

    // ====== ACTIONS UI ======
    private void ajouterTachePrimaire() {
        String n = nomP.getText().trim();
        String d = descP.getText().trim();
        Integer du = parseIntOrNull(dureeP.getText().trim());

        if (n.isEmpty() || du == null) {
            System.out.println("Nom + durée obligatoires");
            return;
        }
        if (d.isEmpty()) d = "(sans description)";
        if (debut.getValue() == null || echeance.getValue() == null) {
            System.out.println("Dates obligatoires");
            return;
        }

        try {
            TachePrimaire tp = new TachePrimaire(
                    n, d, du,
                    toDate(debut.getValue()),
                    toDate(echeance.getValue())
            );

            // ✅ IMPORTANT : ici on passe par le sujet/manager (et lui notifie)
            sujet.ajouterTachePrimaire(tp); // <-- adapte si besoin

            // reset champs
            nomP.clear();
            descP.clear();
            dureeP.clear();

        } catch (Exception ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    private void ajouterSousTache() {
        TachePrimaire parent = cbParent.getValue();
        if (parent == null) {
            System.out.println("Choisis un parent");
            return;
        }

        String n = nomS.getText().trim();
        String d = descS.getText().trim();
        Integer du = parseIntOrNull(dureeS.getText().trim());

        if (n.isEmpty() || du == null) {
            System.out.println("Nom + durée obligatoires");
            return;
        }
        if (d.isEmpty()) d = "(sans description)";

        try {
            SousTache st = new SousTache(n, d, du);

            // ✅ passer par le sujet/manager
            sujet.ajouterSousTache(parent, st); // <-- adapte si besoin

            // reset
            nomS.clear();
            descS.clear();
            dureeS.clear();

        } catch (Exception ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }

    // ====== UTILS ======
    private static Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return null; }
    }

    private static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
