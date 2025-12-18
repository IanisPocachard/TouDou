module TouDou {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop; // <- ajouté pour java.awt

    exports vue;
    exports modele.fabrique;
    exports modele.manager;
    exports modele;
    exports main;
}
