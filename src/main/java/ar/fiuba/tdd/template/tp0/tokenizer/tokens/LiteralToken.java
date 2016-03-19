package ar.fiuba.tdd.template.tp0.tokenizer.tokens;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.Optional;

public class LiteralToken implements Token {
    private final Optional<Quantifier> quantifier;
    private final Character literal;

    public LiteralToken(Character literal, Optional<Quantifier> quantifier) {
        this.literal = literal;
        this.quantifier = quantifier;
    }

    @Override
    public TokenType getType() {
        return TokenType.LITERAL;
    }

}
