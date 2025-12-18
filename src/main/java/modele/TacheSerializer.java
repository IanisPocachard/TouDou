package modele;

import java.io.*;
import java.util.List;

public class TacheSerializer {

    private static final String chemin = "resources/taches.toudou";

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

        if (!f.exists() || f.length() == 0) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            return (List<Tache>) ois.readObject();
        } catch (EOFException e) {
            // sécurité supplémentaire
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
