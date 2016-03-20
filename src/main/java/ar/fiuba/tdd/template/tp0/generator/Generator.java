package ar.fiuba.tdd.template.tp0.generator;

import ar.fiuba.tdd.template.tp0.generator.resolver.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.List;

import static ar.fiuba.tdd.template.tp0.generator.helper.LengthCalculator.getMax;
import static java.util.stream.Collectors.toList;

public class Generator {

    private final GeneratorResolver generatorResolver;

    public Generator(GeneratorResolver generatorResolver) {
        this.generatorResolver = generatorResolver;
    }

    public List<String> generate(List<Token> tokens) {
        return tokens.stream().map(this::generate).collect(toList());
    }

    private String generate(Token token) {
        return this.generatorResolver.resolve(token).generate(token, getMax(token.getQuantifier()));
    }

}
