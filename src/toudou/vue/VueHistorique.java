package vue;

import javafx.scene.control.TextArea;
import modele.Modele;
import modele.Sujet;

public class VueHistorique extends TextArea implements Observateur {

    private Modele modele;

    public VueHistorique(Sujet s) {
        if (s instanceof Modele) {
            this.modele = (Modele) s;
        }
    }

    @Override
    public void actualiser(Sujet s) {

    }

}
