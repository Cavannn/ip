package goldenknight.parser;

import goldenknight.exception.DukeException;

public class Parser {
    public static String[] parse(String input) throws DukeException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new DukeException("OOPS!!! You entered an empty command.");
        }

        return trimmed.split(" ", 2);
    }
}
