package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class RegExGenerator {

    public static final Integer MAX_LENGTH = 5;

    private final Tokenizer tokenizer;
    private final Generator generator;
    private final Integer maxLength;

    public RegExGenerator(Tokenizer tokenizer, Generator generator, Integer maxLength) {
        this.tokenizer = tokenizer;
        this.generator = generator;
        this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, Integer numberOfResults) {
        if (isNull(regEx) || isNull(numberOfResults)) {
            throw new IllegalArgumentException("Regex or number of results can't be empty");
        }
        return generate(this.tokenizer.tokenize(regEx), numberOfResults);
    }

    private List<String> generate(List<Token> tokens, Integer numberOfResults) {
        return range(0, numberOfResults).boxed().map(i -> this.generate(tokens)).collect(toList());
    }

    private String generate(List<Token> tokens) {
        return convertResults(this.generator.generate(tokens));
    }

    private String convertResults(List<String> results) {
        return results.stream().reduce(String::concat).get();
    }
}