package ar.fiuba.tdd.template.tp0.generator.generators;

import ar.fiuba.tdd.template.tp0.generator.helper.RandomCharacterGenerator;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType.*;
import static java.util.Objects.isNull;

public class LengthCalculator {
    private static final Integer ONE = 1;
    private static final EnumMap<QuantifierType, Integer> minByType;
    private static final EnumMap<QuantifierType, Integer> maxByType;

    private static final Integer MAX_DEFINED = 5;
    private static final Random random = new Random();

    static {
        minByType = new EnumMap<>(QuantifierType.class);
        minByType.put(ONE_OR_MANY, 1);
        minByType.put(ZERO_OR_MANY, 0);
        minByType.put(ZERO_OR_ONE, 0);


        maxByType = new EnumMap<>(QuantifierType.class);
        maxByType.put(ONE_OR_MANY, MAX_DEFINED);
        maxByType.put(ZERO_OR_MANY, MAX_DEFINED);
        maxByType.put(ZERO_OR_ONE, 1);
    }

    public static Integer getMin(Optional<Quantifier> optional) {
        return resolve(optional, quantifier -> minByType.get(quantifier.getType()));
    }

    public static Integer getMax(Optional<Quantifier> optional) {
        return random.nextInt(resolve(optional, quantifier -> maxByType.get(quantifier.getType())));
    }

    private static Integer resolve(Optional<Quantifier> optional, Function<Quantifier, Integer> function) {

        if (!optional.isPresent()) {
            return ONE;
        }

        Integer response = function.apply(optional.get());

        if (isNull(response)) {
            throw new IllegalStateException("Can't find length for quantifier " + optional.get());
        }

        return response;
    }

}
