package ar.fiuba.tdd.template.tp0.tokenizer.token;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Token {

    private final TokenType type;
    private final Optional<Quantifier> quantifier;
    private final Set<Character> characterSet;
    private final Character literal;

    public Token(TokenType type, Optional<Quantifier> quantifier) {
        this.type = type;
        this.quantifier = quantifier;
        this.characterSet = new HashSet<>();
        this.literal = '\0';
    }

    public Token(TokenType type, Optional<Quantifier> quantifier, Set<Character> characterSet) {
        this.type = type;
        this.quantifier = quantifier;
        this.characterSet = characterSet;
        this.literal = '\0';
    }

    public Token(TokenType type, Optional<Quantifier> quantifier, Character literal) {
        this.type = type;
        this.quantifier = quantifier;
        this.literal = literal;
        this.characterSet = new HashSet<>();
    }

    public TokenType getType() {
        return this.type;
    }

    public Optional<Quantifier> getQuantifier() {
        return this.quantifier;
    }

    public Set<Character> getCharacterSet() {
        return this.characterSet;
    }

    public Character getLiteral() {
        return this.literal;
    }

}
