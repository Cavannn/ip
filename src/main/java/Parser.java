public class Parser {
    public static String[] parse(String input) throws DukeException {
        if (input.trim().isEmpty()) {
            throw new DukeException("OOPS!!! You entered an empty command.");
        }
        return input.split(" ", 2);
    }
}
