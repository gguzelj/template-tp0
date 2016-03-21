package ar.fiuba.tdd.template.tp0.tokenizer.analyzers;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

public interface TokenResolver {

    Token resolveToken(String regex);

    Boolean supports(Character character);

    String getRemainRegex(String regEx);
}
