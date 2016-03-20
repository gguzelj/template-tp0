package ar.fiuba.tdd.template.tp0.generator.helper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class RandomCharacterGenerator {

    public static final String POSSIBLE_CHARACTERS =
            "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private static final Random random = new Random();

    public static Character getRandomChar() {
        return POSSIBLE_CHARACTERS.charAt(random.nextInt(POSSIBLE_CHARACTERS.length()));
    }

    public static Character getRandomCharFrom(Set<Character> setOfCharacters) {
        return new ArrayList<>(setOfCharacters).get(random.nextInt(setOfCharacters.size()));
    }

}
