package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.resolveQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.LITERAL;

public class LiteralProvider implements TokenProvider {

    @Override
    public Boolean supports(Character character) {
        return isLiteral(character) || isEscape(character);
    }

    @Override
    public Token resolveToken(String regex) {
        checkContext(regex);

        Character literal = getLiteral(regex);
        Optional<Quantifier> quantifier = getQuantifier(regex);

        return new Token(LITERAL, quantifier, literal);
    }

    @Override
    public String getRemainRegex(String regEx) {
        Integer indexOfLiteral = getIndexOfLiteral(regEx);

        Integer begin = 1;
        begin += isEscape(regEx) ? 1 : 0;
        begin += hasQuantifier(regEx.substring(indexOfLiteral)) ? 1 : 0;

        return regEx.substring(begin);
    }

    private Optional<Quantifier> getQuantifier(String regex) {
        Integer indexOfLiteral = getIndexOfLiteral(regex);

        if (hasQuantifier(regex.substring(indexOfLiteral))) {
            return Optional.of(resolveQuantifier(regex.charAt(indexOfLiteral + 1)));
        }

        return Optional.empty();
    }

    private Character getLiteral(String regex) {
        return regex.charAt(getIndexOfLiteral(regex));
    }

    private Integer getIndexOfLiteral(String regex) {
        return isEscape(regex) ? 1 : 0;
    }

    private void checkContext(String regex) {
        if (isEscape(regex.charAt(0)) && !hasNextCharacter(regex)) {
            throw new IllegalRegexException("Literal is not escaping anything");
        }
    }
}

