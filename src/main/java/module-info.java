module com.github.metakol.testtask {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.github.metakol.testtask to javafx.fxml;
    exports com.github.metakol.testtask;
    opens com.github.metakol.testtask.controllers to javafx.fxml;
    exports com.github.metakol.testtask.controllers;
    opens com.github.metakol.testtask.entity to javafx.fxml;
    exports com.github.metakol.testtask.entity;
}
