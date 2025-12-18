package modele;

import java.util.List;

public class testMain {

    public static void main(String[] args) {

        try {
            List<Tache> taches = TacheSerializer.lireFichier();

            if (taches == null || taches.isEmpty()) {
                System.out.println("Le fichier taches.toudou est vide ou inexistant.");
                return;
            }

            System.out.println("Tâches lues depuis taches.toudou :");

            for (Tache t : taches) {
                System.out.println(" - " + t);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier :");
            e.printStackTrace();
        }
    }
}
