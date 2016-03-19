package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.DefaultState;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.State;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;

/**
 * This method analyze each character of the regex, emitting tokens when necessary.
 * i.e. for the regex '..+[ab]*d?c':
 * . -> Emits anyCharacterToken
 * . -> Emits anyCharacterToken (with + quantifier)
 * + -> Does not issue anything
 * [ -> Emits GroupToken (with group [ab])
 * a -> Does not issue anything
 * b -> Does not issue anything
 * ] -> Does not issue anything
 * ...
 */
public class Analyzer {

    private State state;

    public Analyzer() {
        this.state = new DefaultState();
    }

    public Optional<Token> resolveToken(Context context) {
        return this.state.resolveToken(context, this);
    }

    public void setState(State state) {
        this.state = state;
    }
}
