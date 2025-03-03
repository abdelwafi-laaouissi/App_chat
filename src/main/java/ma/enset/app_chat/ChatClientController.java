package ma.enset.app_chat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.*;
import java.net.*;

public class ChatClientController {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private PrintWriter out;

    @FXML
    private VBox messageContainer;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;

    @FXML
    public void initialize() {
        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread pour écouter les messages
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        addMessageToUI("Client 1: " + message, false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            addMessageToUI("Moi: " + message, true);
            out.println(message);
            inputField.clear();
        }
    }

    private void addMessageToUI(String message, boolean isSent) {
        Platform.runLater(() -> { // Exécuter le code sur le thread JavaFX
            Label messageLabel = new Label(message);
            messageLabel.setWrapText(true);
            messageLabel.setMaxWidth(250);

            if (isSent) {
                messageLabel.getStyleClass().add("sent-message");
            } else {
                messageLabel.getStyleClass().add("received-message");
            }

            messageContainer.getChildren().add(messageLabel);
        });
    }
}

