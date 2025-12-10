package modele;

import java.io.*;
import java.util.List;

public class TacheSerializer {

    private static final String chemin = "src/toudou/taches.toudou";

    // ####################
    // # PATRON SINGLETON #
    // ####################
    private static TacheSerializer instance;

    private TacheSerializer() {

    }

    public static TacheSerializer getInstance() {
        if (instance == null) {
            instance = new TacheSerializer();
        }
        return instance;
    }

    // ################
    // # PARTIE OBJET #
    // ################

    public static void ecrireFichier(List<Tache> taches) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(taches);
        }
    }

    public static List<Tache> lireFichier() throws Exception {
        File f = new File(chemin);

        if (!f.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            return (List<Tache>) ois.readObject();
        }
    }

}
