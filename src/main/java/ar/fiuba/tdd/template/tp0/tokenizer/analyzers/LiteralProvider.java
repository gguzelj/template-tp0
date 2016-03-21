package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.resolveQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.LITERAL;

public class LiteralProvider implements TokenProvider {

    @Override
    public Token resolveToken(String regex) {
        checkContext(regex);

        Character literal = getLiteral(regex);
        Optional<Quantifier> quantifier = getQuantifier(regex);

        return new Token(LITERAL, quantifier, literal);
    }

    @Override
    public String getRemainRegex(String regEx) {
        Integer begin = 1;
        if (isEscape(regEx.charAt(0))) {
            begin += 1;
            begin += hasQuantifier(regEx.substring(1)) ? 1 : 0;
        } else {
            begin += hasQuantifier(regEx) ? 1 : 0;
        }
        return regEx.substring(begin);
    }

    private Character getLiteral(String regex) {
        return isEscape(regex.charAt(0)) ? regex.charAt(1) : regex.charAt(0);
    }

    private Optional<Quantifier> getQuantifier(String regex) {
        if (isEscape(regex.charAt(0)) && hasQuantifier(regex.substring(1))) {
            return Optional.of(resolveQuantifier(regex.charAt(2)));
        }

        if (hasQuantifier(regex)) {
            return Optional.of(resolveQuantifier(regex.charAt(1)));
        }

        return Optional.empty();
    }

    private void checkContext(String regex) {
        if (isEscape(regex.charAt(0)) && !hasNextCharacter(regex)) {
            throw new IllegalRegexException("Literal is not escaping anything");
        }
    }

    @Override
    public Boolean supports(Character character) {
        return isLiteral(character) || isEscape(character);
    }
}

