package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.generator.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.enums.TokenType;

public interface Token {

    TokenType getType();

    Boolean hasQuantifier();
    Quantifier getQuantifier();

}
