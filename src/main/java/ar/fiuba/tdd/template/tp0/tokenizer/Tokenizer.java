package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver.TokenResolverProvider;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.ArrayList;
import java.util.List;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver.TokenResolverProvider.getProvider;

public class Tokenizer {

    public List<Token> tokenize(String regEx) {

        List<Token> tokens = new ArrayList<>();

        while (!regEx.isEmpty()) {
            tokens.add(resolveToken(regEx));
            regEx = getRemainRegex(regEx);
        }

        return tokens;
    }

    private String getRemainRegex(String regex) {
        return getProvider(regex).getRemainRegex(regex);
    }

    public Token resolveToken(String regex) {
        return getProvider(regex).resolveToken(regex);
    }

}
