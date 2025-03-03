module ma.enset.app_chat {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens ma.enset.app_chat to javafx.fxml;
    exports ma.enset.app_chat;
}