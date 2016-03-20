package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import static ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator.getRandomCharFrom;
import static java.util.stream.IntStream.range;

public class GroupGenerator implements StringGenerator {

    private static final String EMPTY_STRING = "";

    @Override
    public String generate(Token token, Integer min, Integer max) {
        return range(min, max).boxed()
                .map(i -> getRandomCharFrom(token.getCharacterSet()))
                .map(String::valueOf)
                .reduce(String::concat)
                .orElse(EMPTY_STRING);
    }
}
