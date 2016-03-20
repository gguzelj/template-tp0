package ar.fiuba.tdd.template.tp0.generator.resolver;

import ar.fiuba.tdd.template.tp0.generator.generators.Generators;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.getRandomChar;
import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.getRandomCharFrom;
import static ar.fiuba.tdd.template.tp0.tokenizer.token.TokenType.*;
import static java.util.Objects.isNull;
import static java.util.stream.IntStream.range;

public class GeneratorResolver {

    private static final Map<TokenType, StringGenerator> generators;

    static {
        generators = new EnumMap<>(TokenType.class);

        generators.put(ANY_CHARACTER, Generators::anyCharacterStrategy);
        generators.put(GROUP, Generators::groupStrategy);
        generators.put(LITERAL, Generators::literalStrategy);
    }

    public StringGenerator resolve(Token token) {
        final StringGenerator response = generators.get(token.getType());

        if (isNull(response)) {
            throw new IllegalArgumentException("Unknown strategy for token " + token);
        }

        return response;
    }

}
