package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.AnyCharacterToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isDot;
import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver.hasQuantifier;

public class AnyCharacterState implements State {

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {
        if (hasQuantifier(context)) {
            return Optional.empty();
        }

        analyzer.setState(DEFAULT_STATE);
        return newToken(context);
    }

    private Optional<Token> newToken(Context context) {
        return Optional.of(new AnyCharacterToken(QuantifierResolver.resolve(context)));
    }

    @Override
    public Boolean supports(Context context) {
        return isDot(context);
    }

}
