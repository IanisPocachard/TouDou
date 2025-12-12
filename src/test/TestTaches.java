import modele.SousTache;
import modele.Tache;
import modele.TachePrimaire;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestTaches {

    @Test
    public void test_01_Construction_normale_Tache() throws Exception {
        Tache t = new Tache("Test Tache", "Test des tâches pour vérifier la construction normale",  10);
        assertNotNull(t, "L'objet Tache ne doit pas être null");
        assertEquals("Le nom n'est pas correctement initialisé", t.getNom(), "Test Tache");
        assertEquals("La description n'est pas correctement initialisée", t.getDescription(), "Test des tâches pour vérifier la construction normale");
        assertEquals("La durée de tâche n'est pas correctement initialisée", t.getDuree(), 10);
    }

    @Test
    public void test_02_Construction_normale_TachePrimaire() throws Exception {
        Date date1 = new Date(10/12/2025);
        Date date2 = new Date(12/12/2025);
        TachePrimaire t = new TachePrimaire("Test TachePrimaire", "Test des tâches primaire pour vérifier la construction normale",  1, date1, date2);
        assertNotNull(t, "L'objet TachePrimaire ne doit pas être null");
        assertEquals("Le nom n'est pas correctement initialisé", t.getNom(), "Test TachePrimaire");
        assertEquals("La description n'est pas correctement initialisée", t.getDescription(), "Test des tâches primaire pour vérifier la construction normale");
        assertEquals("La durée de tâche n'est pas correctement initialisée", t.getDuree(), 1);
        assertEquals("La date de début n'est pas correctement initialisée", t.getDateDebut(), date1);
        assertEquals("La date de fin n'est pas correctement initialisée", t.getDateEcheance(), date2);
    }

    @Test
    public void test_03_Constuction_normale_SousTache() throws Exception {
        SousTache st = new SousTache("Sous Tache", "Test pour vérifier la construction normale d'une sous tache", 1);
        assertNotNull(st, "L'objet SousTache ne doit pas être null");
        assertEquals("Le nom n'est pas correctement intialisée", st.getNom(), "Sous Tache");
        assertEquals("La description n'est pas correctement initialisée", st.getDescription(), "Test pour vérifier la construction normale d'une sous tache");
        assertEquals("La durée n'est pas correctement initialisée", st.getDuree(), 1);
    }

    @Test
    public void test_04_Dependance() throws Exception {
        TachePrimaire t = new TachePrimaire("Tache 1", "Test dépendance", 2, new Date(01/12/2025), new Date(12/12/2025));
        SousTache st = new SousTache("Sous tache 1", "Test dépendance", 2);
        t.ajoutDependance(st);
        assertEquals("La liste de dépendance n'est pas correctement initialisée", t.getDependances().size(), 1);
    }

}
