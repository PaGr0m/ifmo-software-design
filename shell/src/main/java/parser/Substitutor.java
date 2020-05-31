package parser;

import service.Environment;

import java.util.List;

public class Substitutor {
    Environment environment = Environment.getInstance();

    public void substitution(List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            if (lexeme.type == LexemType.WORD_VARIABLE) {
                String subs = lexeme.word.substring(lexeme.word.indexOf("$") + 1);
                lexeme.word = lexeme.word.replace("$" + subs, environment.getVariable(subs));
            }

            if (lexeme.type == LexemType.WORD_IN_DOUBLE_QUOTE) {
                if (lexeme.word.contains("$")) {
                    StringBuilder subs = new StringBuilder();
                    subs.append(lexeme.word.substring(lexeme.word.indexOf("$") + 1));
                    subs.deleteCharAt(lexeme.word.length() - 3);
                    lexeme.word = lexeme.word.replace("$" + subs.toString(),
                                                    environment.getVariable(subs.toString()));
                }
            }
        }
    }
}
