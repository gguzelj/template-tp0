package ar.fiuba.tdd.template.tp0.generator.resolver;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

@FunctionalInterface
public interface StringGenerator {
    String generate(Token token, Integer max);
}
