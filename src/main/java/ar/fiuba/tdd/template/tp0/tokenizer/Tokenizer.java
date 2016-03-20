package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
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
                .map(index -> this.analyzer.resolveToken(newContext(index, regex)))
                .collect(toList());
    }

    private Context newContext(Integer index, String regex) {
        return new Context(index, regex.charAt(index), regex);
    }

}
