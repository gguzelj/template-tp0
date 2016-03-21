package ar.fiuba.tdd.template.tp0.tokenizer.helper;

public class Helper {

    public static final String LITERALS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final Character DOT = '.';
    public static final Character ESCAPE = '\\';
    public static final Character OPEN_SQUARE_BRACKET = '[';
    public static final Character CLOSE_SQUARE_BRACKET = ']';

    public static Boolean isLiteral(Character character) {
        return LITERALS.indexOf(character) != -1;
    }

    public static Boolean isEscape(Character character) {
        return ESCAPE.equals(character);
    }

    public static Boolean isGroup(Character character) {
        return OPEN_SQUARE_BRACKET.equals(character);
    }

    public static Boolean isDot(Character character) {
        return DOT.equals(character);
    }

    public static Boolean hasNextCharacter(String regex) {
        return regex.length() > 1;
    }

}
