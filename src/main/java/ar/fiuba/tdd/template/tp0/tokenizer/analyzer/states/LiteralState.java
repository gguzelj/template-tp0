package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Objects;
import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.resolve;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isEscape;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isEscaped;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isLiteral;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.LITERAL;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class LiteralState implements State {

    private Character literal;
    private Optional<Quantifier> quantifier;

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        checkContext(context);

        if (isNull(this.literal)) {
            this.literal = getLiteral(context);
            return (isEscape(context) || hasQuantifier(context)) ? Optional.empty() : returnToken(analyzer);
        }

        if (hasQuantifier(context) && isNull(this.quantifier)) {
            this.quantifier = QuantifierHelper.resolveQuantifier(context);
            return Optional.empty();
        }

        return returnToken(analyzer);
    }

    private Optional<Token> returnToken(Analyzer analyzer) {
        analyzer.setState(DEFAULT_STATE);
        return newToken();
    }

    private void checkContext(Context context) {
        if (isEscape(context) && !context.hasNextCharacter()) {
            throw new IllegalRegexException("Regex can't end with \\");
        }
    }

    private Optional<Token> newToken() {
        final Character character = literal;
        final Optional<Quantifier> quantifier = isNull(this.quantifier) ? Optional.empty() : this.quantifier;
        this.literal = null;
        this.quantifier = null;
        return Optional.of(new Token(LITERAL, quantifier, character));
    }

    private Character getLiteral(Context context) {
        if (isEscape(context)) {
            return context.getNextCharacter().get();
        }
        return context.getCharacter();
    }

    @Override
    public Boolean supports(Context context) {
        return isLiteral(context) || isEscape(context);
    }

}

