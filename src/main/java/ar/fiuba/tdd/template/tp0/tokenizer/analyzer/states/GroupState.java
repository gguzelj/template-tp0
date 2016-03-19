package ar.fiuba.tdd.template.tp0.tokenizer.analyzer.states;

import ar.fiuba.tdd.template.tp0.tokenizer.Context;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.GroupToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.Token;

import java.util.Optional;
import java.util.Set;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isCloseBracket;
import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.isGroup;

public class GroupState implements State {

    private static final String ILLEGAL_CHARACTERS = "[";

    @Override
    public Optional<Token> resolveToken(Context context, Analyzer analyzer) {

        if (isCloseBracket(context)) {
            analyzer.setState(new DefaultState());
            return newToken(context);
        }

        return Optional.empty();
    }

    private Optional<Token> newToken(Context context) {
        Set<Character> group = Helper.getCharacterSetForGroup(context);
        Optional<Quantifier> quantifier = QuantifierResolver.resolve(context);

        return Optional.of(new GroupToken(group, quantifier));
    }

    @Override
    public Boolean supports(Context context) {
        return isGroup(context);
    }

}
