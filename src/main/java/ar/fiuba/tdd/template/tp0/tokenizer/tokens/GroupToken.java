package ar.fiuba.tdd.template.tp0.tokenizer.tokens;

import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.Optional;
import java.util.Set;

public class GroupToken implements Token {

    private final Optional<Quantifier> quantifier;
    private final Set<Character> characterSet;

    public GroupToken(Optional<Quantifier> quantifier, Set<Character> characterSet) {
        this.quantifier = quantifier;
        this.characterSet = characterSet;
    }

    @Override
    public TokenType getType() {
        return TokenType.GROUP;
    }

}
