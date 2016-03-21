package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver.TokenResolverProvider;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<Token> tokenize(String regEx) {
        String remainingRegex = regEx;
        List<Token> response = new ArrayList<>();

        while (!remainingRegex.isEmpty()) {
            response.add(resolveToken(remainingRegex));
            remainingRegex = getRemainRegex(remainingRegex);
        }

        return response;
    }

    private String getRemainRegex(String regex) {
        return TokenResolverProvider.resolve(regex).getRemainRegex(regex);
    }

    public Token resolveToken(String regex) {
        return TokenResolverProvider.resolve(regex).resolveToken(regex);
    }

}
