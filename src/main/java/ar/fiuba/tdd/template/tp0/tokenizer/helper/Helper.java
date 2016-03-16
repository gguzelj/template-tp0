package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

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

        if (open == -1) {
            return Boolean.FALSE;
        }

        return open < index  && index < close;
    }

    public static Boolean isNotInsideGroup(Integer index, String context) {
        return !isInsideGroup(index, context);
    }

    public static Boolean isGroup(Character character) {
        return character.equals(OPEN_SQUARE_BRACKET);
    }

    public static Boolean isDot(Character character) {
        return character.equals(DOT);
    }

    public static Boolean isEscaped(Integer index, String context) {
        if (index == 0) {
            return Boolean.FALSE;
        }
        return isEscape(context.charAt(index - 1));
    }

    public static Boolean isEscape(Character character) {
        return character.equals(ESCAPE);
    }

    public static Boolean isNotEscaped(Integer index, String context) {
        return !isEscaped(index, context);
    }

    public static Boolean hasToEmitAnyCharacterToken(Integer index, Character character, String context) {
        return isDot(character) && isNotEscaped(index, context);
    }

    public static Boolean hasToEmitGroupToken(Integer index, Character character, String context) {
        return isGroup(character) && isNotEscaped(index, context);
    }

    public static Boolean hasToEmitLiteralToken(Integer index, Character character, String context) {
        if (isEscaped(index, context))
            return Boolean.FALSE;
        return (isLiteral(character) || isEscape(character)) && isNotInsideGroup(index, context);
    }

    public static Set<Character> getCharacterSetForGroup(Integer index, String context) {
        final Integer close = context.indexOf(CLOSE_SQUARE_BRACKET, index);
        final String substring = context.substring(index + 1, close);

        return range(0, substring.length()).boxed()
                .map(substring::charAt)
                .collect(toSet());
    }
}
