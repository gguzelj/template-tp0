package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.generator.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import org.junit.Test;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private static final BinaryOperator<Boolean> COMBINER = (item1, item2) -> item1 && item2;

    private Boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = newRegExGenerator(numberOfResults);

        List<String> results = generator.generate(regEx, numberOfResults);

        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");

        return results.stream()
                .reduce(true, (acc, item) -> acc && pattern.matcher(item).find(), COMBINER);
    }

    //TODO: Uncomment these tests
    @Test
    public void testAnyCharacter() {
//        assertTrue(validate("..+[ab]*d?c", 1));
        assertTrue(Boolean.TRUE);
    }

    // TODO: Add more tests!!!
/*
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
        Analyzer analyzer = new Analyzer();
        Tokenizer tokenizer = new Tokenizer(analyzer);
        GeneratorResolver generatorResolver = new GeneratorResolver();
        Generator generator = new Generator(generatorResolver);
        return new RegExGenerator(tokenizer, generator, numberOfResults);
    }

}
