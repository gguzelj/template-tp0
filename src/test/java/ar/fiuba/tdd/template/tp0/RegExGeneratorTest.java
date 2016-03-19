package ar.fiuba.tdd.template.tp0;

import ar.fiuba.tdd.template.tp0.generator.Generator;
import ar.fiuba.tdd.template.tp0.generator.GeneratorResolver;
import ar.fiuba.tdd.template.tp0.tokenizer.Tokenizer;
import ar.fiuba.tdd.template.tp0.tokenizer.analyzer.Analyzer;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private Boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = newRegExGenerator(numberOfResults);

        List<String> results = generator.generate(regEx, numberOfResults);
        if (results.size() != numberOfResults) {
            return Boolean.FALSE;
        }

        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");

        return results.stream()
                .map(result -> pattern.matcher(result).find())
                .allMatch(match -> match == Boolean.TRUE);
    }

//    @Test
//    public void testAnyCharacter() {
//        assertTrue(validate("..+[ab]*d?c", 1));
//    }
//
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
        Tokenizer tokenizer = newTokenizer();
        Generator generator = newGenerator();
        return new RegExGenerator(tokenizer, generator, numberOfResults);
    }

    private Generator newGenerator() {
        GeneratorResolver generatorResolver = new GeneratorResolver();
        return new Generator(generatorResolver);
    }

    private Tokenizer newTokenizer() {
        return new Tokenizer(new Analyzer());
    }

}
