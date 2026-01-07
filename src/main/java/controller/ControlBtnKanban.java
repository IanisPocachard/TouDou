package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Modele;
import vue.VueBureau;

public class ControlBtnKanban implements EventHandler<ActionEvent> {

    private Modele modele;

    public ControlBtnKanban(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        VueBureau vueListe = new VueBureau(modele);
        Stage stage = new Stage();
        stage.setScene(new Scene(vueListe, 1000, 300));
        stage.setTitle("TouDou - Vue Kanban");
        stage.show();
    }

}
