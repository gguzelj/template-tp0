package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.LiteralToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isLiteral;

public class LiteralState implements State {

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        Optional<Character> previousCharacter = context.getPreviousCharacter();
        if (!previousCharacter.isPresent()) {
            return Optional.empty();
        }

        if (previousCharacter.get() == '\\') {
            analyzer.setState(new DefaultState());
            return newToken(context);
        }

        throw new IllegalArgumentException("Unexpected error while creating literal token");
    }

    //TODO
    private Optional<Token> newToken(Context context) {
        Character character = context.getCharacter();
        Optional<Quantifier> quantifier = QuantifierResolver.resolve(context);
        return Optional.of(new LiteralToken(character, quantifier));
    }

    @Override
    public Boolean supports(Context context) {
        return isLiteral(context.getCharacter());
    }

}

