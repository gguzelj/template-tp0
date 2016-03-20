package ar.fiuba.tdd.template.tp0.generator;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.List;

import static ar.fiuba.tdd.template.tp0.generator.generators.LengthCalculator.getMax;
import static ar.fiuba.tdd.template.tp0.generator.generators.LengthCalculator.getMin;
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
        final Integer min = getMin(token.getQuantifier());
        final Integer max = getMax(token.getQuantifier());
        return this.generatorResolver.resolve(token).generate(token, min, max);
    }

}
