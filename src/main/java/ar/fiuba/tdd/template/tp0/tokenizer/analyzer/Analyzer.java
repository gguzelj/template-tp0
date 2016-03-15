package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.AnyCharacterToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.GroupToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.LiteralToken;

import java.util.Optional;

public class Analyzer {

    //Quantifiers
    private static final Character DOT = '.';
    private static final Character PLUS = '+';
    private static final Character ASTERISK = '*';

    private static final String POSSIBLE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&/()=¡@";
    private static final Character ESCAPE = '\\';
    private static final Character OPEN_SQUARE_BRACKET = '[';
    private static final Character CLOSE_SQUARE_BRACKET = ']';

    /**
     * This method analyze each character of the regex, emitting tokens when necessary.
     * i.e. for the regex '..+[ab]*d?c':
     * . -> Emits anyCharacterToken
     * . -> Emits anyCharacterToken (with + quantifier)
     * + -> Does not issue anything
     * [ -> Emits GroupToken (with group [ab])
     * a -> Does not issue anything
     * b -> Does not issue anything
     * ] -> Does not issue anything
     * ...
     */
    public Optional<Token> resolveToken(Integer index, Character character, String context) {

        if (hasToEmit(index, character, context)) {
            return getToken(index, character, context);
        }

        return Optional.empty();
    }

    private Optional<Token> getToken(Integer index, Character character, String context) {
        final TokenType tokenType = determineTokenType(index, character, context);
        final Optional<Quantifier> quantifier = getQuantifier(index, context);

        switch (tokenType) {
            case ANY_CHARACTER:
                return emitAnyCharacterToken(quantifier);
            case GROUP:
                return emitGroupToken(quantifier);
            case LITERAL:
                return emitLiteralToken(quantifier);
            default:
                throw new IllegalStateException("Unknown token type " + tokenType);
        }
    }

    private TokenType determineTokenType(Integer index, Character character, String context) {
        if (hasToEmitAnyCharacterToken(index, character, context)) {
            return TokenType.ANY_CHARACTER;
        }

        if (hasToEmitGroupToken(index, character, context)) {
            checkIllegalCharactersInGroup(index, context);
            return TokenType.GROUP;
        }

        return TokenType.LITERAL;
    }

    private Boolean hasToEmit(Integer index, Character character, String context) {
        return hasToEmitAnyCharacterToken(index, character, context)
                || hasToEmitGroupToken(index, character, context)
                || hasToEmitLiteralToken(index, character, context);
    }

    private Boolean hasToEmitAnyCharacterToken(Integer index, Character character, String context) {
        return isAnyCharacter(character) && isNotEscaped(index, context);
    }

    private Boolean hasToEmitGroupToken(Integer index, Character character, String context) {
        return isGroup(character) && isNotEscaped(index, context);
    }

    private Boolean hasToEmitLiteralToken(Integer index, Character character, String context) {
        return (isLiteral(character) || isEscaped(index, context) ) && isNotInsideGroup(index, context);
    }

    private void checkIllegalCharactersInGroup(Integer index, String context) {
//        Integer to = context.indexOf(CLOSE_SQUARE_BRACKET, index);
//        String group = context.substring(index, to);

    }

    private Boolean isLiteral(Character character) {
        return POSSIBLE_CHARACTERS.indexOf(character) != -1;
    }

    private Boolean isInsideGroup(Integer index, String context) {
        Integer close = context.indexOf(CLOSE_SQUARE_BRACKET, index);
        Integer open = context.substring(0, index).lastIndexOf(OPEN_SQUARE_BRACKET);
        return open < index  && index < close;
    }

    private Boolean isNotInsideGroup(Integer index, String context) {
        return !isInsideGroup(index, context);
    }

    private Boolean isGroup(Character character) {
        return character.equals(OPEN_SQUARE_BRACKET);
    }

    private Boolean isAnyCharacter(Character character) {
        return character.equals(DOT);
    }

    private Boolean isEscaped(Integer index, String context) {
        final Character character = context.charAt((index == 0) ? 0 : index - 1 );
        return character.equals(ESCAPE);
    }

    private Boolean isNotEscaped(Integer index, String context) {
        return !isEscaped(index, context);
    }

    private Optional<Token> emitAnyCharacterToken(Optional<Quantifier> quantifier) {
        return Optional.of(new AnyCharacterToken(quantifier));
    }

    private Optional<Token> emitGroupToken(Optional<Quantifier> quantifier) {
        return Optional.of(new GroupToken(quantifier));
    }

    private Optional<Token> emitLiteralToken(Optional<Quantifier> quantifier) {
        return Optional.of(new LiteralToken(quantifier));
    }

    private Optional<Quantifier> getQuantifier(Integer index, String context) {
        return Optional.empty();
    }

}
