package ar.fiuba.tdd.template.tp0.generator;

import ar.fiuba.tdd.template.tp0.generator.generators.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Generator {

    private static final BinaryOperator<String> COMPARATOR = (one, other) -> one;
    private final GeneratorResolver generatorResolver;

    public Generator(GeneratorResolver generatorResolver) {
        this.generatorResolver = generatorResolver;
    }

    public Map<Token, String> generate(List<Token> tokens) {
        return tokens.stream().collect(toMap(token -> token, this::generate, COMPARATOR));
    }

    private String generate(Token token) {
        final StringGenerator stringGenerator = this.generatorResolver.resolve(token);
        return stringGenerator.generate(token);
    }


}
