package ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

public class AsteriskQuantifier implements Quantifier {

    @Override
    public QuantifierType getType() {
        return QuantifierType.ZERO_OR_MANY;
    }

}
