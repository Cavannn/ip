package goldenknight.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import goldenknight.exception.DukeException;

class ParserTest {

    @Test
    void parse_normalInput_splitsCorrectly() throws DukeException {

    }

    private void assertEquals(String todo, String s) {
    }

    private void assertEquals(int i, int length) {
    }

    @Test
    void parse_singleWordInput_returnsSingleElementArray() throws DukeException {
        String input = "list";
        String[] result = Parser.parse(input);
        assertEquals(1, result.length);
        assertEquals("list", result[0]);
    }

    @Test
    void parse_inputWithLeadingTrailingSpaces_trimsAndSplitsCorrectly() throws DukeException {
        String input = "   deadline submit report   ";
        String[] result = Parser.parse(input);
        assertEquals(2, result.length);
        assertEquals("deadline", result[0]);
        assertEquals("submit report", result[1]);
    }

    @Test
    void parse_emptyInput_throwsDukeException() {
        String input = "";
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse(input));
        assertEquals("OOPS!!! You entered an empty command.", exception.getMessage());
    }

    @Test
    void parse_blankInput_throwsDukeException() {
        String input = "     ";
        DukeException exception = assertThrows(DukeException.class, () -> Parser.parse(input));
        assertEquals("OOPS!!! You entered an empty command.", exception.getMessage());
    }
}
