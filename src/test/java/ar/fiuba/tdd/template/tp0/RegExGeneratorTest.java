package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.generator.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.TokenTypeResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.quantifier.QuantifierResolver;
import org.junit.Test;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private Boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = newRegExGenerator(numberOfResults);

        List<String> results = generator.generate(regEx, numberOfResults);

        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");

        return ( results.size() == numberOfResults )
                && results.stream()
                .map(result -> pattern.matcher(result).find())
                .allMatch(match -> match == Boolean.TRUE);
    }

    /*
    @Test
    public void testAnyCharacter() {
        assertTrue(validate("..+[ab]*d?c", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }
*/
    private RegExGenerator newRegExGenerator(int numberOfResults) {
        QuantifierResolver quantifierResolver = new QuantifierResolver();
        TokenTypeResolver tokenTypeResolver = new TokenTypeResolver();
        Analyzer analyzer = new Analyzer(quantifierResolver, tokenTypeResolver);
        Tokenizer tokenizer = new Tokenizer(analyzer);
        GeneratorResolver generatorResolver = new GeneratorResolver();
        Generator generator = new Generator(generatorResolver);
        return new RegExGenerator(tokenizer, generator, numberOfResults);
    }

}
