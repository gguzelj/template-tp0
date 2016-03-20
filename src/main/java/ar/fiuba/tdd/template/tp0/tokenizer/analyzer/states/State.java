package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.token.Token;

import java.util.Optional;

public interface State {

    Optional<Token> resolveToken(Context context, Analyzer analyzer);

    Boolean supports(Context context);

}
