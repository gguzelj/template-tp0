package ar.fiuba.tdd.template.tp0.tokenizer;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.TokenResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver.TokenResolverProvider;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public List<Token> tokenize(String regEx) {
        String remainingRegex = regEx;
        List<Token> response = new ArrayList<>();

        while (!remainingRegex.isEmpty()) {
            TokenResolver resolve = TokenResolverProvider.resolve(remainingRegex.charAt(0));
            response.add(resolve.resolveToken(remainingRegex));
            remainingRegex = resolve.getRemainRegex(remainingRegex);
        }

        return response;
    }

}
