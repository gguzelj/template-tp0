package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

public class Helper {

    public static final Character DOT = '.';

    public static final String POSSIBLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&/()=¡@";
    public static final Character ESCAPE = '\\';
    public static final Character OPEN_SQUARE_BRACKET = '[';
    public static final Character CLOSE_SQUARE_BRACKET = ']';


    public static Boolean isLiteral(Character character) {
        return POSSIBLE_CHARACTERS.indexOf(character) != -1;
    }

    public static Boolean isInsideGroup(Integer index, String context) {
        Integer close = context.indexOf(CLOSE_SQUARE_BRACKET, index);
        Integer open = context.substring(0, index).lastIndexOf(OPEN_SQUARE_BRACKET);
        return open < index  && index < close;
    }

    public static Boolean isNotInsideGroup(Integer index, String context) {
        return !isInsideGroup(index, context);
    }

    public static Boolean isGroup(Character character) {
        return character.equals(OPEN_SQUARE_BRACKET);
    }

    public static Boolean isAnyCharacter(Character character) {
        return character.equals(DOT);
    }

    public static Boolean isEscaped(Integer index, String context) {
        final Character character = context.charAt((index == 0) ? 0 : index - 1 );
        return character.equals(ESCAPE);
    }

    public static Boolean isNotEscaped(Integer index, String context) {
        return !isEscaped(index, context);
    }

}
