package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class RegExGenerator {

    private final Tokenizer tokenizer;
    private final Generator generator;

    public RegExGenerator(Tokenizer tokenizer, Generator generator) {
        this.tokenizer = requireNonNull(tokenizer, "Tokenizer can't be null");
        this.generator = requireNonNull(generator, "Generator can't be null");
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