package ar.fiuba.tdd.template.tp0.tokenizer.quantifier;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.AsteriskQuantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.PlusQuantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.QuestionMarkQuantifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.CLOSE_SQUARE_BRACKET;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isQuantifier;

public class QuantifierResolver {

    private static final Character PLUS = '+';
    private static final Character ASTERISK = '*';
    private static final Character QUESTION_MARK = '?';

    private static final Map<Character, Quantifier> quantifiers;

    static {
        quantifiers = new HashMap<>();

        quantifiers.put(PLUS, new PlusQuantifier());
        quantifiers.put(ASTERISK, new AsteriskQuantifier());
        quantifiers.put(QUESTION_MARK, new QuestionMarkQuantifier());
    }

    public static Optional<Quantifier> resolve(Context context) {
        return (isQuantifier(context)) ? getNextQuantifier(context) : Optional.empty();
    }

    public static Boolean hasQuantifier(Context context) {
        Optional<Character> nextCharacter = context.getNextCharacter();
        if (!nextCharacter.isPresent()) {
            return Boolean.FALSE;
        }
        return quantifiers.containsKey(nextCharacter.get());
    }

    public static Optional<Quantifier> getNextQuantifier(Context context) {
        final String regex = context.getRegex();
        final Integer index = context.getIndex();
        final String substring = regex.substring(index, regex.length());

        return substring.chars().mapToObj(i -> (char) i)
                .filter(quantifiers::containsKey)
                .map(quantifiers::get)
                .findFirst();
    }


    public static Boolean isQuantifier(Context context) {
        return isQuantifier(context.getCharacter());
    }
    
    public static Boolean isQuantifier(Character character) {
        return quantifiers.containsKey(character);
    }

}
