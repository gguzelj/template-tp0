package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType;

import java.util.Optional;
import java.util.Set;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.*;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

public class GroupProvider implements TokenProvider {

    @Override
    public Token resolveToken(String regex) {
        checkIllegalCharacters(regex);

        Set<Character> characters = getSet(regex);
        Optional<Quantifier> quantifier = getQuantifier(regex);

        return new Token(TokenType.GROUP, quantifier, characters);
    }

    @Override
    public Boolean supports(Character character) {
        return isGroup(character);
    }

    @Override
    public String getRemainRegex(String regex) {
        Integer indexOfCloseGroup = findIndexOfCloseGroup(regex, 0);
        if (hasQuantifier(regex.substring(indexOfCloseGroup))) {
            indexOfCloseGroup++;
        }
        return regex.substring(indexOfCloseGroup + 1);
    }

    private String getGroup(String regex) {
        final Integer close = findIndexOfCloseGroup(regex, 0);
        return regex.substring(1, close);
    }

    private Optional<Quantifier> getQuantifier(String regex) {
        String remainRegex = getRemainRegex(regex);
        if (remainRegex.isEmpty()) {
            return Optional.empty();
        }
        return isQuantifier(remainRegex.charAt(0)) ? Optional.of(resolveQuantifier(remainRegex.charAt(0))) : Optional.empty();
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

    private Set<Character> getSet(String regex) {
        Set<Character> set = getSetOfCharacters(regex);
        if (set.isEmpty()) {
            throw new IllegalRegexException("Empty group!");
        }
        return set;
    }

    private void checkIllegalCharacters(String regex) {
        String group = getGroup(regex);
        Set<Character> illegals = getIllegals(group);

        if (!illegals.isEmpty()) {
            illegals.forEach(illegal -> checkIllegal(illegal, group));
        }
    }

    private void checkIllegal(Character illegal, String group) {
        range(0, group.length()).boxed()
                .forEach(index -> {
                        if (illegal.equals(group.charAt(index))) {
                            checkIfIllegalIsEscaped(index, group);
                        }
                    });
    }

    private void checkIfIllegalIsEscaped(Integer index, String group) {
        if (index == 0 || !isEscape(group.charAt(index - 1))) {
            throw new IllegalRegexException("Illegal character not escaped");
        }
    }

    public Set<Character> getIllegals(String group) {
        return range(0, group.length()).boxed()
                .map(group::charAt)
                .filter(this::isIllegalCharacter)
                .collect(toSet());
    }

    private Boolean isIllegalCharacter(Character character) {
        return LITERALS.indexOf(character) == -1 && !isEscape(character);
    }

    private Set<Character> getSetOfCharacters(String regex) {
        final String substring = getGroup(regex);
        return range(0, substring.length()).boxed()
                .map(substring::charAt)
                .filter(character -> !isEscape(character))
                .collect(toSet());
    }
}
