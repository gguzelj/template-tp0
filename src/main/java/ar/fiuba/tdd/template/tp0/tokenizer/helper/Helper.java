package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;

import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.POSSIBLE_CHARACTERS;

public class Helper {

    public static final Character DOT = '.';
    public static final Character ESCAPE = '\\';
    public static final Character OPEN_SQUARE_BRACKET = '[';
    public static final Character CLOSE_SQUARE_BRACKET = ']';

    public static Boolean isLiteral(Context context) {
        return isLiteral(context.getCharacter());
    }

    public static Boolean isLiteral(Character character) {
        return POSSIBLE_CHARACTERS.indexOf(character) != -1;
    }

    public static Boolean isGroup(Context context) {
        return OPEN_SQUARE_BRACKET.equals(context.getCharacter());
    }

    public static Boolean isDot(Context context) {
        return DOT.equals(context.getCharacter());
    }

    public static Boolean isEscaped(Context context) {
        if (!context.hasPreviousCharacter()) {
            return Boolean.FALSE;
        }
        return isEscape(context.getPreviousCharacter().get());
    }

    public static Boolean isEscape(Context context) {
        return isEscape(context.getCharacter());
    }

    public static Boolean isEscape(Character character) {
        return ESCAPE.equals(character);
    }

    public static Boolean isCloseBracket(Context context) {
        return isCloseBracket(context.getCharacter());
    }

    public static Boolean isCloseBracket(Character character) {
        return CLOSE_SQUARE_BRACKET.equals(character);
    }

    public static Boolean isOpenBracket(Context context) {
        return OPEN_SQUARE_BRACKET.equals(context.getCharacter());
    }

}
