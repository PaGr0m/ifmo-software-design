package parser;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void testParseLexemWithWordInDoubleQuote() {
        // Arrange
        String input = "\"   hello   world   \"";
        List<Lexeme> excpected = Collections.singletonList(
                Lexeme.builder()
                     .word(input)
                     .type(LexemType.WORD_IN_DOUBLE_QUOTE)
                     .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(excpected);
    }


    @Test
    public void testParseLexemWithWordInSingleQuote() {
        // Arrange
        String input = "'  hello   world  '";
        List<Lexeme> excpected = Collections.singletonList(
                Lexeme.builder()
                     .word(input)
                     .type(LexemType.WORD_IN_SINGLE_QUOTE)
                     .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(excpected);
    }

    @Test
    public void testParseLexemWithWordVariable() {
        // Arrange
        String input = "$task";
        List<Lexeme> excpected = Collections.singletonList(
                Lexeme.builder()
                     .word(input)
                     .type(LexemType.WORD_VARIABLE)
                     .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(excpected);
    }

    @Test
    public void testParseLexemWithWordAssigment() {
        // Arrange
        String input = "y=10";
        List<Lexeme> excpected = Collections.singletonList(
                Lexeme.builder()
                     .word(input)
                     .type(LexemType.WORD_ASSIGMENT)
                     .build());

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(excpected);
    }

    @Test
    public void testParseLexem() {
        // Arrange
        String input = "Hello World \"    Software  Design\"  $bash 'hi' ";
        Lexeme lexeme1 = Lexeme.builder().word("Hello").type(LexemType.WORD).build();
        Lexeme lexeme2 = Lexeme.builder().word("World").type(LexemType.WORD).build();
        Lexeme lexeme3 = Lexeme.builder().word("\"    Software  Design\"").type(LexemType.WORD_IN_DOUBLE_QUOTE).build();
        Lexeme lexeme4 = Lexeme.builder().word("$bash").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexeme5 = Lexeme.builder().word("'hi'").type(LexemType.WORD_IN_SINGLE_QUOTE).build();
        List<Lexeme> excpected = Arrays.asList(lexeme1, lexeme2, lexeme3, lexeme4, lexeme5);

        // Act
        List<Lexeme> actual = parser.parse(input);

        // Assert
        assertThat(actual).isEqualTo(excpected);
    }
}
