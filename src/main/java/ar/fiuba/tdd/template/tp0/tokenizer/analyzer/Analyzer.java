package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.AnyCharacterToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.GroupToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.LiteralToken;

import java.util.Optional;

import static ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Helper.*;

public class Analyzer {

    private final QuantifierResolver quantifierResolver;

    public Analyzer(QuantifierResolver quantifierResolver) {
        this.quantifierResolver = quantifierResolver;
    }

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
        final TokenType tokenType = this.determineTokenType(index, character, context);
        final Optional<Quantifier> quantifier = this.quantifierResolver.resolve(index, context);

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

    private Optional<Token> emitAnyCharacterToken(Optional<Quantifier> quantifier) {
        return Optional.of(new AnyCharacterToken(quantifier));
    }

    private Optional<Token> emitGroupToken(Optional<Quantifier> quantifier) {
        return Optional.of(new GroupToken(quantifier));
    }

    private Optional<Token> emitLiteralToken(Optional<Quantifier> quantifier) {
        return Optional.of(new LiteralToken(quantifier));
    }

}
