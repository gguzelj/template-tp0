package ar.fiuba.tdd.template.tp0.tokenizer.quantifier;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.AsteriskQuantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.PlusQuantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers.QuestionMarkQuantifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Helper.CLOSE_SQUARE_BRACKET;
import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Helper.isInsideGroup;

public class QuantifierResolver {

    private static final String PLUS = "+";
    private static final String ASTERISK = "*";
    private static final String QUESTION_MARK = "?";

    private static final Map<String, Quantifier> quantifiers;

    static {
        quantifiers = new HashMap<>();

        quantifiers.put(PLUS, new PlusQuantifier());
        quantifiers.put(ASTERISK, new AsteriskQuantifier());
        quantifiers.put(QUESTION_MARK, new QuestionMarkQuantifier());
    }

    public Optional<Quantifier> resolve(Integer index, String context) {

        if (isInsideGroup(index, context)) {
            return this.getQuantifierForGroup(index, context);
        }

        if (isQuantifier(nextChar(index, context))) {
            return this.resolve(nextChar(index, context));
        }

        return Optional.empty();
    }

    private Optional<Quantifier> resolve(String quantifier) {
        return Optional.of(quantifiers.get(quantifier));
    }

    private Optional<Quantifier> getQuantifierForGroup(Integer index, String context) {
        Integer endOfGroup = context.indexOf(CLOSE_SQUARE_BRACKET, index);
        return resolve(endOfGroup, context);
    }

    private Boolean isQuantifier(String character) {
        return quantifiers.containsKey(character);
    }

    private String nextChar(Integer index, String context) {
        return "" + context.charAt(index + 1);
    }
}
