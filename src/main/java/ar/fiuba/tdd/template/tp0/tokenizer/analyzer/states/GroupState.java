package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver.DEFAULT_STATE;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class GroupState implements State {

    public static final String GROUP_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Set<Character> group = null;

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        if (isNull(group)) {
            buildSet(context);
        }

        if (hasToEmit(context)) {
            analyzer.setState(DEFAULT_STATE);
            return newToken(context);
        }

        return Optional.empty();
    }

    private Boolean hasToEmit(Context context) {
        return (isCloseBracket(context) && !nextCharacterIsQuantifier(context) && !isEscaped(context))
                || (isQuantifier(context) && previousCharacterEndsGroup(context));
    }

    private Boolean nextCharacterIsQuantifier(Context context) {
        if (!context.hasNextCharacter()) {
            return Boolean.FALSE;
        }
        return isQuantifier(context.getNextCharacter().get());
    }

    private Boolean previousCharacterEndsGroup(Context context) {
        if (!context.hasPreviousCharacter()) {
            return Boolean.FALSE;
        }

        return isCloseBracket(context.getPreviousCharacter().get());
    }


    private void buildSet(Context context) {
        this.group = getCharacterSetForGroup(context);
        checkSet(context);
    }

    private void checkSet(Context context) {
        if (this.group.isEmpty()) {
            this.group = null;
            throw new IllegalRegexException("Empty group!");
        }
        checkIllegalCharacters(context);
    }

    private void checkIllegalCharacters(Context context) {
        Set<Character> illegals = getIllegals();

        if (!illegals.isEmpty()) {
            String setAsString = getSetAsString(context);
            illegals.forEach(illegal -> checkIllegal(illegal, setAsString));
        }
    }

    private void checkIllegal(Character illegal, String setAsString) {
        IntStream.range(0, setAsString.length()).boxed()
                .forEach(index -> {
                    if (illegal.equals(setAsString.charAt(index))) {
                        checkIfIllegalIsEscaped(index, setAsString);
                    }
                });
    }

    private void checkIfIllegalIsEscaped(Integer index, String setAsString) {
        if (index == 0 || !isEscape(setAsString.charAt(index - 1))) {
            this.group = null;
            throw new IllegalRegexException("Illegal character not escaped");
        }
    }

    public Set<Character> getIllegals() {
        return this.group.stream().filter(this::isIllegalCharacter).collect(toSet());
    }

    private Boolean isIllegalCharacter(Character character) {
        return GROUP_CHARACTERS.indexOf(character) == -1 && !isEscape(character);
    }

    private String getSetAsString(Context context) {
        final Integer close = findIndexOfCloseGroup(context.getRegex(), context.getIndex());
        return context.getRegex().substring(context.getIndex() + 1, close);
    }

    private Integer findIndexOfCloseGroup(String regex, Integer index) {
        Integer response = regex.indexOf(CLOSE_SQUARE_BRACKET, index);
        if (response == -1) {
            throw new IllegalRegexException("Unclosed group!");
        }

        if (isEscape(regex.charAt(response - 1))) {
            return findIndexOfCloseGroup(regex, response + 1);
        }
        return response;
    }


    private Set<Character> getCharacterSetForGroup(Context context) {
        final String substring = getSetAsString(context);
        return IntStream.range(0, substring.length()).boxed()
                .map(substring::charAt)
                .filter(character -> !isEscape(character))
                .collect(toSet());
    }

    private Optional<Token> newToken(Context context) {
        Set<Character> group = this.group;
        this.group = null;
        Optional<Quantifier> quantifier = QuantifierHelper.resolveQuantifier(context);

        return Optional.of(new Token(TokenType.GROUP, quantifier, group));
    }

    @Override
    public Boolean supports(Context context) {
        return isGroup(context);
    }
}
