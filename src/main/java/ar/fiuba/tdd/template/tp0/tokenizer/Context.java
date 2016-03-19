package ar.fiuba.tdd.template.tp0.tokenizer;

import java.util.Optional;

public class Context {

    private final Integer index;
    private final Character character;
    private final String regex;

    public Context(Integer index, Character character, String regex) {
        this.index = index;
        this.character = character;
        this.regex = regex;
    }

    public Integer getIndex() {
        return index;
    }

    public Character getCharacter() {
        return character;
    }

    public String getRegex() {
        return regex;
    }

    public Optional<Character> getNextCharacter() {
        if (this.index == regex.length() - 1) {
            return Optional.empty();
        }
        return Optional.of(regex.charAt(index + 1));
    }

    public Optional<Character> getPreviousCharacter() {
        if (this.index == 0) {
            return Optional.empty();
        }
        return Optional.of(regex.charAt(index - 1));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Context{");
        sb.append("index=").append(index);
        sb.append(", character=").append(character);
        sb.append(", regex='").append(regex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
