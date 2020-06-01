package parser;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void testParseLexemeWithWordInDoubleQuote() {
        // Arrange
        String input = "\"   hello   world   \"";
        List<Lexeme> expected = Collections.singletonList(
                Lexeme.builder()
                      .word(input)
                      .type(LexemType.WORD_IN_DOUBLE_QUOTE)
                      .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParseLexemeWithWordInSingleQuote() {
        // Arrange
        String input = "'  hello   world  '";
        List<Lexeme> expected = Collections.singletonList(
                Lexeme.builder()
                      .word(input)
                      .type(LexemType.WORD_IN_SINGLE_QUOTE)
                      .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParseLexemeWithWordVariable() {
        // Arrange
        String input = "$task";
        List<Lexeme> expected = Collections.singletonList(
                Lexeme.builder()
                      .word(input)
                      .type(LexemType.WORD_VARIABLE)
                      .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParseLexemeWithWordAssigment() {
        // Arrange
        String input = "y=10";
        List<Lexeme> expected = Collections.singletonList(
                Lexeme.builder()
                      .word(input)
                      .type(LexemType.WORD_ASSIGMENT)
                      .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParseLexeme() {
        // Arrange
        String input = "Hello World \"    Software  Design\"  $bash 'hi' ";
        Lexeme lexeme1 = Lexeme.builder().word("Hello").type(LexemType.WORD).build();
        Lexeme lexeme2 = Lexeme.builder().word(" ").type(LexemType.SPACE).build();
        Lexeme lexeme3 = Lexeme.builder().word("World").type(LexemType.WORD).build();
        Lexeme lexeme4 = Lexeme.builder().word(" ").type(LexemType.SPACE).build();
        Lexeme lexeme5 = Lexeme.builder().word("\"    Software  Design\"").type(LexemType.WORD_IN_DOUBLE_QUOTE).build();
        Lexeme lexeme6 = Lexeme.builder().word(" ").type(LexemType.SPACE).build();
        Lexeme lexeme7 = Lexeme.builder().word("$bash").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexeme8 = Lexeme.builder().word(" ").type(LexemType.SPACE).build();
        Lexeme lexeme9 = Lexeme.builder().word("'hi'").type(LexemType.WORD_IN_SINGLE_QUOTE).build();
        Lexeme lexeme10 = Lexeme.builder().word(" ").type(LexemType.SPACE).build();
        List<Lexeme> expected = Arrays.asList(lexeme1, lexeme2, lexeme3, lexeme4, lexeme5,
                                              lexeme6, lexeme7, lexeme8, lexeme9, lexeme10);

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testParseLexeme2() {
        // Arrange
        String input = "$x$y";
        Lexeme lexeme1 = Lexeme.builder().word("$x").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexeme2 = Lexeme.builder().word("$y").type(LexemType.WORD_VARIABLE).build();
        List<Lexeme> expected = Arrays.asList(lexeme1, lexeme2);

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
