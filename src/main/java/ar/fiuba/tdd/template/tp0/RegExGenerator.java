package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;

import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.range;

public class RegExGenerator {

    private static final String EMPTY_STRING = "";
    private static final IntUnaryOperator TEST = i -> i;

    private final Tokenizer tokenizer;
    private final Generator generator;
    private final Integer maxLength;

    public RegExGenerator(Tokenizer tokenizer, Generator generator, Integer maxLength) {
        this.tokenizer = tokenizer;
        this.generator = generator;
        this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, Integer numberOfResults) {
        if (isNull(regEx) || isNull(numberOfResults)){
            throw new IllegalArgumentException("Regex or number of results can't be empty");
        }

        final List<Token> tokens = this.tokenizer.tokenize(regEx);

        return range(0, numberOfResults)
                .mapToObj(i -> this.generate(tokens))
                .collect(toList());
    }

    private String generate(List<Token> tokens) {
        if (isNull(tokens))
            throw new IllegalStateException("No tokens provided to generate string");
        return mapResults(this.generator.generate(tokens));
    }

    private String mapResults(Map<Token, String> results) {
        final Optional<String> reduce = results.values().stream().reduce(String::concat);
        return (reduce.isPresent()) ? reduce.get() : EMPTY_STRING;
    }
}