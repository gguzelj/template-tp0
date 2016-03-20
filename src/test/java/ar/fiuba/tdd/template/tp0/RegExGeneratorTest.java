package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.exception.IllegalRegexException;
import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.generator.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private RegExGenerator generator;

    private Boolean validate(String regEx, Integer numberOfResults) {
        List<String> results = this.generator.generate(regEx, numberOfResults);
        if (results.size() != numberOfResults) {
            return Boolean.FALSE;
        }

        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$", Pattern.UNICODE_CHARACTER_CLASS);

        return results.stream()
                .map(result -> pattern.matcher(result).find())
                .allMatch(match -> match == Boolean.TRUE);
    }

    @Before
    public void setUp() {
        this.generator = newRegExGenerator();
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate("..+[ab]*d?c", 1000));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1000));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1000));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1000));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1000));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1000));
    }

//    @Test
//    public void testCharacterSet2() {
//        assertTrue(validate("\\+P?A?.+\\-*[abc]", 1));
//    }

//    @Test
//    public void testEscapedCharacterOnGroup() {
        //TODO accept escaped characters on group
//        assertTrue(validate("[as\\+]", 1000));
//    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnEmptyGroup() {
        assertTrue(validate("[]", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnEmptyGroup2() {
        assertTrue(validate("a.?[]+", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnMalformedEmptyGroup() {
        assertTrue(validate("[as+]", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnMalformedEmptyGroup2() {
        assertTrue(validate("[a[]", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnUnopenedGroup() {
        assertTrue(validate("]", 1000));
    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnUnopenedGroup2() {
        assertTrue(validate("..]+", 1000));
    }

//    @Test(expected = IllegalRegexException.class)
//    public void testShouldFailOnMalformedGroup() {
        //TODO Should fail with a '.' within group?
//        assertTrue(validate("[.a]", 1000));
//    }

    @Test(expected = IllegalRegexException.class)
    public void testShouldFailOnUnassignedQuantifier() {
        assertTrue(validate("+", 1000));
    }

//    @Test(expected = IllegalRegexException.class)
//    public void testShouldFailOnUnescapedCharacter() {
//        assertTrue(validate("..\\", 1000));
//    }
//
//    @Test(expected = IllegalRegexException.class)
//    public void testShouldFailOnUnescapedCharacter2() {
//        assertTrue(validate("\\", 1000));
//    }

    @Test(expected = NullPointerException.class)
    public void testShouldFailOnEmptyTokenizer() {
        new RegExGenerator(null, newGenerator());
    }

    @Test(expected = NullPointerException.class)
    public void testShouldFailOnEmptyGenerator() {
        new RegExGenerator(newTokenizer(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateFailOnEmptyRegex() {
        this.generator.generate(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldFailOnEmptyNumberOfResults() {
        this.generator.generate("", null);
    }

    private RegExGenerator newRegExGenerator() {
        Tokenizer tokenizer = newTokenizer();
        Generator generator = newGenerator();
        return new RegExGenerator(tokenizer, generator);
    }

    private Generator newGenerator() {
        GeneratorResolver generatorResolver = new GeneratorResolver();
        return new Generator(generatorResolver);
    }

    private Tokenizer newTokenizer() {
        return new Tokenizer(new Analyzer());
    }

}
