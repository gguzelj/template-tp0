package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.generator.StringGenerator;
import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.enums.TokenType;

import java.util.EnumMap;
import java.util.Map;

import static ar.fiuba.tdd.template.tp0.tokenizer.enums.TokenType.*;
import static java.util.Objects.isNull;

public class GeneratorResolver {

    private static final Map<TokenType, StringGenerator> generators;

    static {
        generators = new EnumMap<>(TokenType.class);

        generators.put(ANY_CHARACTER, new AnyCharacterGenerator());
        generators.put(GROUP, new GroupGenerator());
        generators.put(LITERAL, new LiteralGenerator());
    }

    public StringGenerator resolve(Token token) {
        final StringGenerator response = generators.get(token.getType());

        if (isNull(response)){
            throw new IllegalArgumentException("Unknown token " + token);
        }

        return response;
    }
}