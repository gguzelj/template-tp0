package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.function.Function;

import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.getRandomChar;
import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.getRandomCharFrom;
import static java.util.stream.IntStream.range;

public class Generators {

    private static final String EMPTY_STRING = "";

    public static String anyCharacterStrategy(Token token, Integer max) {
        return generate(max, i -> getRandomChar());
    }

    public static String groupStrategy(Token token, Integer max) {
        return generate(max, i -> getRandomCharFrom(token.getCharacterSet()));
    }

    public static String literalStrategy(Token token, Integer max) {
        return generate(max, i -> token.getLiteral());
    }

    private static String generate(Integer max, Function<Integer, Character> mapper) {
        return range(0, max).boxed()
                .map(mapper)
                .map(String::valueOf)
                .reduce(String::concat)
                .orElse(EMPTY_STRING);
    }
}
