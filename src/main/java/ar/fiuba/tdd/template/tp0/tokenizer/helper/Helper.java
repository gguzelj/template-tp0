package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

public class Helper {

    public static final Character DOT = '.';

    public static final String POSSIBLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&/()=¡@";
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
        return isGroup(context.getCharacter());
    }

    public static Boolean isGroup(Character character) {
        return character.equals(OPEN_SQUARE_BRACKET);
    }

    public static Boolean isDot(Context context) {
        return isDot(context.getCharacter());
    }

    public static Boolean isDot(Character character) {
        return DOT.equals(character);
    }

    public static Boolean isEscape(Context context) {
        return isEscape(context.getCharacter());
    }

    public static Boolean isEscape(Character character) {
        return ESCAPE.equals(character);
    }

    public static Boolean isCloseBracket(Character character) {
        return CLOSE_SQUARE_BRACKET.equals(character);
    }

    public static Boolean isOpenBracket(Context context) {
        return OPEN_SQUARE_BRACKET.equals(context.getCharacter());
    }

    public static Set<Character> getCharacterSetForGroup(Context context) {
        final Integer close = context.getRegex().indexOf(CLOSE_SQUARE_BRACKET, context.getIndex());
        final String substring = context.getRegex().substring(context.getIndex() + 1, close);

        return range(0, substring.length()).boxed()
                .map(substring::charAt)
                .collect(toSet());
    }

}
