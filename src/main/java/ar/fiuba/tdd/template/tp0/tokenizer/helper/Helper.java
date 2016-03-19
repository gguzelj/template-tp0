package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;

import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
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

    public static Boolean isGroup(Context context) {
        return isGroup(context.getCharacter());
    }

    public static Boolean isGroup(Character character) {
        return character.equals(OPEN_SQUARE_BRACKET);
    }

    public static Boolean isDot(Context context) {
        return DOT.equals(context.getCharacter());
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

    public static Set<Character> getCharacterSetForGroup(Context context) {
        final Integer close = context.getRegex().indexOf(CLOSE_SQUARE_BRACKET, context.getIndex());
        final String substring = context.getRegex().substring(context.getIndex() + 1, close);

        return range(0, substring.length()).boxed()
                .map(substring::charAt)
                .collect(toSet());
    }

    public static Boolean isQuantifier(Context context) {
        return QuantifierResolver.isQuantifier(context);
    }

    public static Boolean isCloseBracket(Character character) {
        return CLOSE_SQUARE_BRACKET.equals(character);
    }

    public static Boolean isOpenBracket(Context context) {
        return OPEN_SQUARE_BRACKET.equals(context.getCharacter());
    }

}
