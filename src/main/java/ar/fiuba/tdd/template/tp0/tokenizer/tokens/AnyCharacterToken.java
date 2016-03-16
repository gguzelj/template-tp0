package ar.fiuba.tdd.template.tp0.tokenizer.tokens;

import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.Optional;

public class AnyCharacterToken implements Token {

    private final Optional<Quantifier> quantifier;

    public AnyCharacterToken(Optional<Quantifier> quantifier) {
        this.quantifier = quantifier;
    }

    @Override
    public TokenType getType() {
        return TokenType.ANY_CHARACTER;
    }

}
