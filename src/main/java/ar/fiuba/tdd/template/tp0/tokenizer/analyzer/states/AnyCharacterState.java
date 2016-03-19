package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.AnyCharacterToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isDot;

public class AnyCharacterState implements State {

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {
        analyzer.setState(new DefaultState());
        return newToken(context);
    }

    //TODO
    private Optional<Token> newToken(Context context) {
        Optional<Quantifier> quantifier = QuantifierResolver.resolve(context);
        return Optional.of(new AnyCharacterToken(quantifier));
    }

    @Override
    public Boolean supports(Context context) {
        return isDot(context);
    }

}
