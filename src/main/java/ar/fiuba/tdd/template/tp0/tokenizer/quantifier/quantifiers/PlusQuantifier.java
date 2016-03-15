package ar.fiuba.tdd.template.tp0.tokenizer.quantifier.quantifiers;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

public class PlusQuantifier implements Quantifier {

    @Override
    public QuantifierType getType() {
        return QuantifierType.ONE_OR_MANY;
    }

}
