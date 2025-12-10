import modele.Tache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTachesPrimaire {

    @Test
    public void test_01_Construction_normale(){
        Tache t = new Tache("Test", "Test des tâches pour vérifier la construction normale", 1);
        assertNotNull(t, "L'objet Tache ne doit pas être null");
        assertEquals("Le nom n'est pas correctement initialisé", t.getNom(), "Test");
        assertEquals("La description n'est pas correctement initialisée", t.getDescription(), "Test des tâches pour vérifier la construction normale");
        assertEquals("Le numéro de tâche n'est pas correctement initialisée", t.getNumTache(), 1);
    }

    @

}
