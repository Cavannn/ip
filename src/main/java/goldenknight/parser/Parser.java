package goldenknight.parser;

import goldenknight.exception.DukeException;

/**
 * Parses user input into a command keyword and its arguments.
 *
 * <p>This class provides a static method to split a user's raw input
 * string into at most two parts: the command keyword (first word)
 * and the rest of the input as arguments.</p>
 *
 * <p>Leading and trailing spaces in the input are ignored. If the input
 * is empty or contains only whitespace, a {@link DukeException} is thrown.</p>
 */
public class Parser {

    /**
     * Splits the given input string into a command keyword and arguments.
     *
     * <p>The input is first trimmed of leading and trailing whitespace. Then
     * it is split into at most two parts: the command keyword (index 0)
     * and the arguments (index 1, if present).</p>
     *
     * @param input The raw input string from the user.
     * @return A string array containing the command keyword and its arguments.
     * @throws DukeException If the input is empty or contains only whitespace.
     */
    public static String[] parse(String input) throws DukeException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new DukeException("OOPS!!! You entered an empty command.");
        }

        return trimmed.split(" ", 2);
    }
}
