import java.io.IOException;

import goldenknight.GoldenKnight;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class to launch the GoldenKnight GUI using FXML.
 */
public class Main extends Application {

    private final GoldenKnight goldenKnight = new GoldenKnight("./data/goldenknight.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = fxmlLoader.load();

            // Inject the GoldenKnight instance into the controller
            MainWindow controller = fxmlLoader.getController();
            controller.setGoldenKnight(goldenKnight);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setTitle("GoldenKnight Chatbot");
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setMaxWidth(417);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
