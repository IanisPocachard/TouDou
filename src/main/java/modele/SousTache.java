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

    public void toggleValide(){
        this.validee = !this.validee;
    }


    public String toString(int indent){
        String res="\t".repeat(indent)+ "Nom de la tache : "+nom+ '\n' + "\t".repeat(indent) + "Description de la  tache : "+description+ '\n' + "\t".repeat(indent) + '\n';
        if(this.getValide()){
            res+="Etat : validé";
        }else{
            res +="Etat : à faire";
        }
        return res;
    }
}
