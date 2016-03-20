package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;

public class DefaultState implements State {

    private static final String ILLEGAL_CHARACTERS = "]";

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        if (isQuantifier(context) || containsIllegalCharacters(context)) {
            throw new IllegalRegexException("Unexpected character here: " + context);
        }

        analyzer.setState(StateResolver.resolve(context));

        return analyzer.resolveToken(context);
    }

    private Boolean containsIllegalCharacters(Context context) {
        return ILLEGAL_CHARACTERS.indexOf(context.getCharacter()) != -1;
    }

    @Override
    public Boolean supports(Context context) {
        return Boolean.TRUE;
    }

}
