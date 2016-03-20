package ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QuantifierTypeResolver {

    private static final Character PLUS = '+';
    private static final Character ASTERISK = '*';
    private static final Character QUESTION_MARK = '?';

    private static final Map<Character, QuantifierType> quantifiers;

    static {
        quantifiers = new HashMap<>();

        quantifiers.put(PLUS, QuantifierType.PLUS);
        quantifiers.put(ASTERISK, QuantifierType.ASTERISCK);
        quantifiers.put(QUESTION_MARK, QuantifierType.QUESTION_MARK);
    }

    public static Optional<Quantifier> resolve(Context context) {
        return (isQuantifier(context)) ? getNextQuantifier(context) : Optional.empty();
    }

    public static Boolean hasQuantifier(Context context) {
        if (!context.hasNextCharacter()) {
            return Boolean.FALSE;
        }
        return quantifiers.containsKey(context.getNextCharacter().get());
    }

    public static Optional<Quantifier> getNextQuantifier(Context context) {
        final String regex = context.getRegex();
        final Integer index = context.getIndex();
        final String substring = regex.substring(index, regex.length());

        return substring.chars().mapToObj(i -> (char) i)
                .filter(quantifiers::containsKey)
                .map(quantifiers::get)
                .map(Quantifier::new)
                .findFirst();
    }


    public static Boolean isQuantifier(Context context) {
        return isQuantifier(context.getCharacter());
    }
    
    public static Boolean isQuantifier(Character character) {
        return quantifiers.containsKey(character);
    }

}
