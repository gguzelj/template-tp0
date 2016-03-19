package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.GroupToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;
import java.util.Set;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.resolver.QuantifierResolver.isQuantifier;

public class GroupState implements State {

    private static final String ILLEGAL_CHARACTERS = "[]()";
    private Set<Character> group;

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        checkGroup(context);

        if (isQuantifier(context) || previousCharacterEndsGroup(context)) {
            analyzer.setState(DEFAULT_STATE);
            return newToken(context);
        }

        return Optional.empty();
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
                throw new IllegalArgumentException("Empty group!");
            }

            this.group.forEach(this::checkCharacter);
        }
    }

    private void checkCharacter(Character character) {
        if ((ILLEGAL_CHARACTERS.indexOf(character) != -1) || isQuantifier(character)) {
            throw new IllegalArgumentException("Illegal character in group");
        }
    }

    private Optional<Token> newToken(Context context) {
        Set<Character> group = this.group;
        Optional<Quantifier> quantifier = QuantifierResolver.resolve(context);

        return Optional.of(new GroupToken(group, quantifier));
    }

    @Override
    public Boolean supports(Context context) {
        return isGroup(context);
    }

}