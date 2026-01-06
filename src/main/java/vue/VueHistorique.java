package vue;

import javafx.scene.control.TextArea;
import modele.Modele;
import modele.Sujet;

public class VueHistorique extends TextArea implements Observateur {

    private Modele modele;
    private String texte;

    public VueHistorique(Modele m) {
        this.modele = m;
        this.texte = "Bienvenue sur TouDou !";
        this.setText(texte);

        this.setEditable(false);
        this.setWrapText(true);

        modele.ajouterObservateur(this);
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Modele m) {
            String message = m.getLog();
            if (message != null && !message.isEmpty()) {
                String ancienTexte = this.getText();
                this.setText(message + "\n" + ancienTexte);
            }
        }
    }
}
