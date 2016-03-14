package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;

public interface Token {

    TokenType getType();

    Boolean hasQuantifier();
    Quantifier getQuantifier();

}
