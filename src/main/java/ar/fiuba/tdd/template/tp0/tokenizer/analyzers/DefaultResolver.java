package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.QuantifierHelper.isQuantifier;

public class DefaultResolver implements TokenResolver {

    @Override
    public Token resolveToken(String regex) {
        throw new IllegalRegexException("Unexpected on regex" + regex);
    }

    @Override
    public Boolean supports(Character character) {
        return Boolean.TRUE;
    }

    @Override
    public String getRemainRegex(String regEx) {
        return regEx;
    }
}
