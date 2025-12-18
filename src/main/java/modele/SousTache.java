package modele;

public class SousTache extends Tache {
    private boolean validee;

    public SousTache(String nom, String description, int duree) throws Exception {
        super(nom, description, duree);
        this.validee = false;
    }

    public boolean getValide(){
        return this.validee;
    }

    public void setEtat(int netat) throws Exception {
        throw new Exception("une sous-tache ne peut changer d'état");
    }

    public void setValide(boolean valide){
        this.validee = valide;
    }
}
