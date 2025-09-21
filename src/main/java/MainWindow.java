import goldenknight.GoldenKnight;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI of GoldenKnight.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private GoldenKnight goldenKnight;

    private static final String LINE = "_______________________________________";

    private final Image userImage = new Image(
            this.getClass().getResourceAsStream("/images/princesstower.png"));
    private final Image botImage = new Image(
            this.getClass().getResourceAsStream("/images/goldenknight.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the GoldenKnight backend instance.
     */
    public void setGoldenKnight(GoldenKnight gk) {
        goldenKnight = gk;

        // Show welcome message on GUI
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(goldenKnight.getWelcomeMessage(), botImage)
        );

        // Show next task reminder
        String reminderMessage = goldenKnight.getNextTaskReminder();
        if (reminderMessage != null && !reminderMessage.isBlank()) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getDukeDialog(reminderMessage, botImage)
            );
        }
    }

    /**
     * Handles user input from the text field.
     * Calls the corresponding GoldenKnight method and updates the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String response;

        // Split command into parts
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        try {
            switch (command) {
            case "list":
                response = goldenKnight.listTasks();
                break;
            case "todo":
                response = goldenKnight.addTodo(parts.length > 1 ? parts[1] : "");
                break;
            case "deadline":
                response = goldenKnight.addDeadline(parts.length > 1 ? parts[1] : "");
                break;
            case "event":
                response = goldenKnight.addEvent(parts.length > 1 ? parts[1] : "");
                break;
            case "mark":
                response = goldenKnight.markTask(parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : -1);
                break;
            case "unmark":
                response = goldenKnight.unmarkTask(parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : -1);
                break;
            case "delete":
                response = goldenKnight.deleteTask(parts.length > 1 ? Integer.parseInt(parts[1]) - 1 : -1);
                break;
            case "find":
                response = goldenKnight.findTasks(parts.length > 1 ? parts[1] : "");
                break;
            case "help":
                response = goldenKnight.getHelpMessage();
                break;
            case "bye":
                response = goldenKnight.getGoodbyeMessage();
                break;
            default:
                response = "I don't understand that command.";
            }
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }

        // Add user input and GoldenKnight response to the dialog container
        boolean isError = response.startsWith("Error:") || response.startsWith("I don't understand");
        if (isError) {
            response = LINE + "\n" + response + "\n" + LINE;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                isError ? DialogBox.getErrorDialog(response, botImage) : DialogBox.getDukeDialog(response, botImage)
        );

        // Clear user input
        userInput.clear();
    }
}
