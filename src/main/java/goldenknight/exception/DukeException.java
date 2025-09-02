package goldenknight.exception;

/**
 * Represents a custom exception used in the GoldenKnight application.
 * This exception is thrown when the user inputs invalid commands
 * or when other application-specific errors occur.
 */
public class DukeException extends Exception {

    /**
     * Constructs a new DukeException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public DukeException(String message) {
        super(message);
    }
}