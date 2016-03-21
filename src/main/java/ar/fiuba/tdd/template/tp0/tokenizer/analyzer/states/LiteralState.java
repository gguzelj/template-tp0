package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.LITERAL;
import static java.util.Objects.isNull;

public class LiteralState implements State {

    private Character literal;
    private Optional<Quantifier> quantifier;
    private Boolean escapingEscape; //don't like this...

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        checkContext(context);

        if (isEscape(context) && isNull(this.escapingEscape)) {
            this.escapingEscape = escapingEscape(context);
            return Optional.empty();
        }

        if (isNull(this.literal)) {
            this.literal = context.getCharacter();
        }

        if (hasQuantifier(context) && isNull(this.quantifier)) {
            this.quantifier = Optional.empty();
            return Optional.empty();
        }

        return returnToken(analyzer, context);
    }

    private Boolean escapingEscape(Context context) {
        return isEscape(context.getNextCharacter().get()) ? Boolean.TRUE : Boolean.FALSE;
    }

    private Optional<Token> returnToken(Analyzer analyzer, Context context) {
        analyzer.setState(DEFAULT_STATE);
        return newToken(context);
    }

    private void checkContext(Context context) {
        if (isEscape(context) && !context.hasNextCharacter()) {
            throw new IllegalRegexException("Regex can't end with \\");
        }
    }

    private Optional<Token> newToken(Context context) {
        final Character character = literal;
        final Optional<Quantifier> quantifier = isNull(this.quantifier) ? Optional.empty() : QuantifierHelper.resolveQuantifier(context);
        this.literal = null;
        this.quantifier = null;
        this.escapingEscape = null;
        return Optional.of(new Token(LITERAL, quantifier, character));
    }

    @Override
    public Boolean supports(Context context) {
        return isLiteral(context) || isEscape(context);
    }

}

