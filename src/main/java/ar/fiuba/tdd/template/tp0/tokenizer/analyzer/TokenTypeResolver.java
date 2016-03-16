package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.hasToEmitAnyCharacterToken;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.hasToEmitGroupToken;

public class TokenTypeResolver {
    public TokenType resolve(Integer index, Character character, String context) {
        if (hasToEmitAnyCharacterToken(index, character, context)) {
            return TokenType.ANY_CHARACTER;
        }

        if (hasToEmitGroupToken(index, character, context)) {
            return TokenType.GROUP;
        }

        return TokenType.LITERAL;
    }
}
