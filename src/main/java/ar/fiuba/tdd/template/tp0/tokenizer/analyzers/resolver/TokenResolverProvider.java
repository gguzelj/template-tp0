package ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.*;

import java.util.ArrayList;
import java.util.List;

public class TokenResolverProvider {

    public static final TokenResolver DEFAULT_STATE = new DefaultResolver();

    private static final List<TokenResolver> states;
    private static final TokenResolver ANY_CHARACTER_STATE = new AnyCharacterResolver();
    private static final TokenResolver GROUP_STATE = new GroupResolver();
    private static final TokenResolver LITERAL_STATE = new LiteralResolver();

    static {
        states = new ArrayList<>();

        states.add(ANY_CHARACTER_STATE);
        states.add(GROUP_STATE);
        states.add(LITERAL_STATE);
    }

    public static TokenResolver resolve(Character character) {
        return states.stream()
                .filter(state -> state.supports(character))
                .findFirst()
                .orElse(DEFAULT_STATE);
    }
}
