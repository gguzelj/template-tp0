package ar.fiuba.tdd.template.tp0.tokenizer;

/**
 * Enum that represents all types of tokens managed:
 *
 *  ANY_CHARACTER: .
 *  GROUP: [c_1, c_2, ... , c_n]
 *  LITERAL: c_1
 *
 *  Where c_i represents any ASCII character
 */
public enum TokenType {
    ANY_CHARACTER, GROUP, LITERAL;
}
