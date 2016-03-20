package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType;

import java.util.Optional;
import java.util.Set;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;

public class GroupState implements State {

    private static final String ILLEGAL_CHARACTERS = "[]()";
    private Set<Character> group;

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        checkGroup(context);

        if (nextCharacterIsQuantifier(context) || previousCharacterEndsGroup(context)) {
            analyzer.setState(DEFAULT_STATE);
            return newToken(context);
        }

        return Optional.empty();
    }

    private Boolean nextCharacterIsQuantifier(Context context) {
        if (!context.hasNextCharacter()) {
            return Boolean.TRUE;
        }
        return isQuantifier(context);
    }

    private Boolean previousCharacterEndsGroup(Context context) {
        if (!context.hasPreviousCharacter()) {
            return Boolean.FALSE;
        }

        return isCloseBracket(context.getPreviousCharacter().get());
    }

    private void checkGroup(Context context) {
        if (isOpenBracket(context)) {
            this.group = getCharacterSetForGroup(context);
            if (this.group.isEmpty()) {
                throw new IllegalRegexException("Empty group!");
            }

            this.group.forEach(this::checkCharacter);
        }
    }

    private void checkCharacter(Character character) {
        if ((ILLEGAL_CHARACTERS.indexOf(character) != -1) || isQuantifier(character)) {
            throw new IllegalRegexException("Illegal character in group");
        }
    }

    private Optional<Token> newToken(Context context) {
        Set<Character> group = this.group;
        Optional<Quantifier> quantifier = QuantifierHelper.resolveQuantifier(context);

        return Optional.of(new Token(TokenType.GROUP, quantifier, group));
    }

    @Override
    public Boolean supports(Context context) {
        return isGroup(context);
    }

}
