package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isDot;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.hasQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.resolveQuantifier;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.ANY_CHARACTER;

public class AnyCharacterProvider implements TokenProvider {

    @Override
    public Token resolveToken(String regex) {
        return new Token(ANY_CHARACTER, getQuantifier(regex));
    }

    private Optional<Quantifier> getQuantifier(String regex) {
        return hasQuantifier(regex) ? Optional.of(resolveQuantifier(regex.charAt(1))) : Optional.empty();
    }

    @Override
    public Boolean supports(Character character) {
        return isDot(character);
    }

    @Override
    public String getRemainRegex(String regEx) {
        if (hasQuantifier(regEx)) {
            return regEx.substring(2);
        }
        return regEx.substring(1);
    }

}
