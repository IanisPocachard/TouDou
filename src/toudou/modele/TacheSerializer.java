package modele;

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
}
