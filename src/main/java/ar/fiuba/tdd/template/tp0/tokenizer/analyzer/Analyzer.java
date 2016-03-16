package ar.fiuba.tdd.template.tp0.tokenizer.analyzer;

import ar.fiuba.tdd.template.tp0.tokenizer.Token;
import ar.fiuba.tdd.template.tp0.tokenizer.TokenType;
import ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.Quantifier;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.AnyCharacterToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.GroupToken;
import ar.fiuba.tdd.template.tp0.tokenizer.tokens.LiteralToken;

import java.util.Optional;
import java.util.Set;

import static ar.fiuba.tdd.template.tp0.tokenizer.helper.Helper.*;

public class Analyzer {

    private final QuantifierResolver quantifierResolver;
    private final TokenTypeResolver tokenTypeResolver;

    public Analyzer(QuantifierResolver quantifierResolver, TokenTypeResolver tokenTypeResolver) {
        this.quantifierResolver = quantifierResolver;
        this.tokenTypeResolver = tokenTypeResolver;
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
        final TokenType tokenType = this.tokenTypeResolver.resolve(index, character, context);
        final Optional<Quantifier> quantifier = this.quantifierResolver.resolve(index, context);

        switch (tokenType) {
            case ANY_CHARACTER:
                return emitAnyCharacterToken(quantifier);
            case GROUP:
                return emitGroupToken(quantifier, this.determineSet(index, context));
            case LITERAL:
                return emitLiteralToken(quantifier, this.determineLiteral(index, character, context));
            default:
                throw new IllegalStateException("Unknown token type " + tokenType);
        }
    }

    private Character determineLiteral(Integer index, Character character, String context) {
        return getLiteral(index, character, context);
    }

    private Set<Character> determineSet(Integer index, String context) {
        return getCharacterSetForGroup(index, context);
    }

    private Boolean hasToEmit(Integer index, Character character, String context) {
        return hasToEmitAnyCharacterToken(index, character, context)
                || hasToEmitGroupToken(index, character, context)
                || hasToEmitLiteralToken(index, character, context);
    }

    private Optional<Token> emitAnyCharacterToken(Optional<Quantifier> quantifier) {
        return Optional.of(new AnyCharacterToken(quantifier));
    }

    private Optional<Token> emitGroupToken(Optional<Quantifier> quantifier, Set<Character> characterSet) {
        return Optional.of(new GroupToken(characterSet, quantifier));
    }

    private Optional<Token> emitLiteralToken(Optional<Quantifier> quantifier, Character literal) {
        return Optional.of(new LiteralToken(literal, quantifier));
    }

}
