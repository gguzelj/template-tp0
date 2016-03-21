package ar.fiuba.tdd.template.tp0.tokenizer.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public static Quantifier resolveQuantifier(Character character) {
        QuantifierType quantifierType = quantifierTypesMap.get(character);
        if (Objects.isNull(quantifierType)) {
            throw new IllegalStateException("Quantifier not found for " + character);
        }
        return new Quantifier(quantifierType);
    }

    public static Boolean hasQuantifier(String regex) {
        if (!hasNextCharacter(regex)) {
            return Boolean.FALSE;
        }
        return quantifierTypesMap.containsKey(getNextCharacter(regex));
    }

    public static Boolean isQuantifier(Character character) {
        return quantifierTypesMap.containsKey(character);
    }

    public static Boolean hasNextCharacter(String regex) {
        return regex.length() > 1;
    }

    public static Character getNextCharacter(String regex) {
        return regex.charAt(1);
    }

}
