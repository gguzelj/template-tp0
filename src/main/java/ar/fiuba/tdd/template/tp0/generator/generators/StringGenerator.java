package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

public interface StringGenerator {
    String generate(Token token, Integer min, Integer max);
}
