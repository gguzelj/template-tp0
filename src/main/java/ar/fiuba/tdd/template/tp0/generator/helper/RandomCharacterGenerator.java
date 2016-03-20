package ar.fiuba.tdd.template.tp0.generator.helper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class RandomCharacterGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&/()=¡@";
    private static final Random random = new Random();

    public static Character getRandomChar() {
        return CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
    }

    public static Character getRandomCharFrom(Set<Character> setOfCharacters) {
        return new ArrayList<>(setOfCharacters).get(random.nextInt(setOfCharacters.size()));
    }

}
