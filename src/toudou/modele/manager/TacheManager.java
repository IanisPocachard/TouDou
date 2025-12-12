package modele.manager;

import modele.Tache;

import java.util.ArrayList;
import java.util.List;

public class TacheManager implements Manager {

    private List<Tache> taches;

    public TacheManager() {
        try {
            List<Tache> temp = new ArrayList<Tache>();
            if (temp == null) {
                taches = new ArrayList();
            } else {
                taches = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            taches = new ArrayList();
        }
    }

}
