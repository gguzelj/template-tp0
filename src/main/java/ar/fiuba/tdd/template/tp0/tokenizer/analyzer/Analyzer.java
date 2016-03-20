package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.State;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states.resolver.StateResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

/**
 * This method analyze each character of the regex, emitting token when necessary.
 * i.e. for the regex '..+[ab]*d?c':
 * . -> Emits anyCharacterToken
 * . -> Does not emit anything
 * + -> Emits anyCharacterToken (with + quantifier)
 * [ -> Does not emit anything
 * a -> Does not emit anything
 * b -> Does not emit anything
 * ] -> Does not emit anything
 * * -> Emits GroupToken (with group [ab] and * quantifier)
 * d -> Does not emit anything
 * ? -> Emits LiteralToken (with ? quantifier)
 * c -> Emits LiteralToken
 * Since every type of token has different behavior (i.e. it's illegal to have a [ inside
 * a group), a state pattern is used and each state performs it's own validations,
 * and emits a token when necessary.
 */
public class Analyzer {

    private State state;

    public Analyzer() {
        this.state = StateResolver.DEFAULT_STATE;
    }

    public Optional<Token> resolveToken(Context context) {
        return this.state.resolveToken(context, this);
    }

    public void setState(State state) {
        this.state = state;
    }
}
