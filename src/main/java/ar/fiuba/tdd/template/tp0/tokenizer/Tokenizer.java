package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Tokenizer {

    private final Analyzer analyzer;

    public Tokenizer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public List<Token> tokenize(String regEx) {
        return createTokens(regEx).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    private List<Optional<Token>> createTokens(String regex) {
        return range(0, regex.length()).boxed()
                .map(index -> this.analyzer.resolveToken(index, regex.charAt(index), regex))
                .collect(toList());
    }

}
