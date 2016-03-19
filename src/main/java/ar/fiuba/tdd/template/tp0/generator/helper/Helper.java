package ar.fiuba.tdd.template.tp0.generator.helper;

import java.util.Random;

public class Helper {

    private static final Random random = new Random();

    public static Character getRandomChar() {
        random.nextInt();
        return Character.MAX_HIGH_SURROGATE;
    }


}
