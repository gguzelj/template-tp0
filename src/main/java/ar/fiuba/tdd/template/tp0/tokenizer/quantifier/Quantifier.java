package ar.fiuba.tdd.template.tp0.tokenizer.quantifier;

public class Quantifier {

    private final QuantifierType type;

    public Quantifier(QuantifierType type) {
        this.type = type;
    }

    public QuantifierType getType() {
        return type;
    }
}
