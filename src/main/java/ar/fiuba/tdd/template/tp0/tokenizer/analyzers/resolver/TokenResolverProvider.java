package ar.fiuba.tdd.template.tp0.tokenizer.analyzers.resolver;

import ar.fiuba.tdd.template.tp0.tokenizer.analyzers.*;

import java.util.ArrayList;
import java.util.List;

public class TokenResolverProvider {

    public static final TokenProvider DEFAULT_STATE = new DefaultProvider();

    private static final List<TokenProvider> states;
    private static final TokenProvider ANY_CHARACTER_STATE = new AnyCharacterProvider();
    private static final TokenProvider GROUP_STATE = new GroupProvider();
    private static final TokenProvider LITERAL_STATE = new LiteralProvider();

    static {
        states = new ArrayList<>();

        states.add(ANY_CHARACTER_STATE);
        states.add(GROUP_STATE);
        states.add(LITERAL_STATE);
    }

    public static TokenProvider resolve(String regex) {
        return states.stream()
                .filter(state -> state.supports(regex.charAt(0)))
                .findFirst()
                .orElse(DEFAULT_STATE);
    }
}
