package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

import java.util.Optional;
import java.util.Set;

public interface Token {

    TokenType getType();

//    Optional<Quantifier> getQuantifier();

//    Set<Character> getLiterals();

}
