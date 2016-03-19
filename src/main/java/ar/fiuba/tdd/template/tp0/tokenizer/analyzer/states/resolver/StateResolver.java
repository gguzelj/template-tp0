package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.*;

import java.util.ArrayList;
import java.util.List;

public class StateResolver {

    public static final State DEFAULT_STATE = new DefaultState();

    private static final List<State> states;
    private static final State ANY_CHARACTER_STATE = new AnyCharacterState();
    private static final State GROUP_STATE = new GroupState();
    private static final State LITERAL_STATE = new LiteralState();

    static {
        states = new ArrayList<>();

        states.add(ANY_CHARACTER_STATE);
        states.add(GROUP_STATE);
        states.add(LITERAL_STATE);
    }

    public static State resolve(Context context) {
        return states.stream()
                .filter(state -> state.supports(context))
                .findFirst()
                .orElse(DEFAULT_STATE);
    }

}
