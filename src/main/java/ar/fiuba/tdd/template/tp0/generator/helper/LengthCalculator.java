package ar.fiuba.tdd.template.tp0.generator.helper;

import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType;

import java.util.EnumMap;
import java.util.Optional;
import java.util.Random;

import static ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierType.*;
import static java.util.Objects.isNull;

public class LengthCalculator {
    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    private static final Integer MAX_DEFINED = 15;
    private static final Random random = new Random();
    private static final EnumMap<QuantifierType, Integer> maxByType;

    static {
        maxByType = new EnumMap<>(QuantifierType.class);
        maxByType.put(PLUS, MAX_DEFINED);
        maxByType.put(ASTERISK, MAX_DEFINED);
        maxByType.put(QUESTION_MARK, ONE);
    }

    public static Integer getMax(Optional<Quantifier> optional) {
        final Integer response = getRandomMax(optional);
        return (ZERO.equals(response)) ? ONE : response;
    }

    private static Integer getRandomMax(Optional<Quantifier> optional) {
        return random.nextInt(resolve(optional));
    }

    private static Integer resolve(Optional<Quantifier> optional) {
        if (!optional.isPresent()) {
            return ONE;
        }

        Integer response = maxByType.get(optional.get().getType());

        if (isNull(response)) {
            throw new IllegalStateException("Can't find length for quantifier " + optional.get());
        }

        return response;
    }

}
