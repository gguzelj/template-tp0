package ar.fiuba.tdd.template.tp0.tokenizer.tokens;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.Optional;
import java.util.Set;

public class GroupToken implements Token {

    private final Optional<Quantifier> quantifier;
    private final Set<Character> characterSet;

    public GroupToken(Set<Character> characterSet, Optional<Quantifier> quantifier) {
        this.quantifier = quantifier;
        this.characterSet = characterSet;
    }

    @Override
    public TokenType getType() {
        return TokenType.GROUP;
    }

}
