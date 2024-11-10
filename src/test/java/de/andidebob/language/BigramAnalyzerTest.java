package de.andidebob.language;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BigramAnalyzerTest {

    private BigramAnalyzer analyzer = new BigramAnalyzer(new EnglishLanguageModel());

    @Test
    void containsOnlyOnePunctuationCharacter() {
        String[] punctuationCharacters = new String[]{
                "!",
                ".",
                ":",
                ";",
                "?",
                "|",
                ",",
                "-"
        };
        for (String punctuationCharacter : punctuationCharacters) {
            String before = punctuationCharacter + "a";
            String after = "a" + punctuationCharacter;
            String doublet = punctuationCharacter + punctuationCharacter;
            assertTrue(analyzer.containsOnlyOnePunctuationCharacter(before), "Expected only one punctuation character to be true for '" + before + "'!");
            assertTrue(analyzer.containsOnlyOnePunctuationCharacter(after), "Expected only one punctuation character to be true for '" + after + "'!");
            assertFalse(analyzer.containsOnlyOnePunctuationCharacter(doublet), "Expected only one punctuation character to be false for '" + doublet + "'!");
        }
        assertFalse(analyzer.containsOnlyOnePunctuationCharacter("aa"));
    }

    @Test
    void containsNonAscii() {
        assertFalse(analyzer.containsNonAscii("Hello!"));
        assertTrue(analyzer.containsNonAscii("Hello, 世界!"));
        assertFalse(analyzer.containsNonAscii("ASCII only text"));
        assertTrue(analyzer.containsNonAscii("😊"));
    }
}