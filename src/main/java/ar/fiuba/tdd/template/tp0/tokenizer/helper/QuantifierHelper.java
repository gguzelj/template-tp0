package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QuantifierHelper {

    private static final Character PLUS = '+';
    private static final Character ASTERISK = '*';
    private static final Character QUESTION_MARK = '?';

    private static final Map<Character, QuantifierType> quantifierTypesMap;

    static {
        quantifierTypesMap = new HashMap<>();

        quantifierTypesMap.put(PLUS, QuantifierType.PLUS);
        quantifierTypesMap.put(ASTERISK, QuantifierType.ASTERISK);
        quantifierTypesMap.put(QUESTION_MARK, QuantifierType.QUESTION_MARK);
    }

    public static Optional<Quantifier> resolveQuantifier(Context context) {
        return (isQuantifier(context)) ? getNextQuantifier(context) : Optional.empty();
    }

    public static Boolean hasQuantifier(Context context) {
        if (!context.hasNextCharacter()) {
            return Boolean.FALSE;
        }
        return quantifierTypesMap.containsKey(context.getNextCharacter().get());
    }

    public static Optional<Quantifier> getNextQuantifier(Context context) {
        final String regex = context.getRegex();
        final Integer index = context.getIndex();
        final String substring = regex.substring(index, regex.length());

        return substring.chars().mapToObj(i -> (char) i)
                .filter(quantifierTypesMap::containsKey)
                .map(quantifierTypesMap::get)
                .map(Quantifier::new)
                .findFirst();
    }


    public static Boolean isQuantifier(Context context) {
        return isQuantifier(context.getCharacter());
    }
    
    public static Boolean isQuantifier(Character character) {
        return quantifierTypesMap.containsKey(character);
    }

}
