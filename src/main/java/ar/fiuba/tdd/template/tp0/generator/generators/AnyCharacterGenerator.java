package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;
import java.util.Random;

import static java.util.Objects.isNull;

public class AnyCharacterGenerator implements StringGenerator {

    private static final String EMPTY_STRING = "";

    private final Random charGenerator;

    public AnyCharacterGenerator(Random charGenerator) {
        this.charGenerator = charGenerator;
    }

    @Override
    public String generate(Token token) {

        if (isNull(token)) {
            throw new IllegalArgumentException("Token can't be null");
        }

        final Optional<String> reduce = charGenerator.ints(10)
                .mapToObj(String::valueOf)
                .reduce(String::concat);

        return (reduce.isPresent()) ? reduce.get() : EMPTY_STRING;
    }
}
