package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.LiteralToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isEscape;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isLiteral;
import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver.isQuantifier;

public class LiteralState implements State {

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        checkContext(context);

        if (isEscape(context) || hasQuantifier(context)) {
            return Optional.empty();
        }

        if (isQuantifier(context) || !hasQuantifier(context)) {
            analyzer.setState(DEFAULT_STATE);
            return newToken(context);
        }

        throw new IllegalArgumentException("Unexpected error while creating literal token for context " + context);
    }

    private void checkContext(Context context) {
        if (isEscape(context) && !context.hasNextCharacter()) {
            throw new IllegalArgumentException("Regex can't end with \\");
        }
    }

    private Optional<Token> newToken(Context context) {
        final Character character = getLiteral(context);
        final Optional<Quantifier> quantifier = QuantifierResolver.resolve(context);
        return Optional.of(new LiteralToken(character, quantifier));
    }

    private Character getLiteral(Context context) {
        if (isQuantifier(context)) {
            return context.getPreviousCharacter().get();
        }

        return context.getCharacter();
    }

    @Override
    public Boolean supports(Context context) {
        return isLiteral(context) || isEscape(context);
    }

}

