package de.andidebob.vigenere.kasiski;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KasiskiAnalyzerTest {

    // Threshold is 1%
    private static final double PASSWORD_CERTAINTY_THRESHOLD = 0.01;

    private final HashMap<String, String> testData = new HashMap<>();
    private final HashMap<String, String> impossibleTestData = new HashMap<>();
    private final KasiskiAnalyzer kasiskiAnalyzer = new KasiskiAnalyzer();

    @BeforeEach
    void initializeTestData() {
        testData.clear();
        testData.put("BROKEN", "Evgzmgf kvo hvtkoxx evdpvi bg kverqfi, hri pizznvro tcxxvolsn tybpwxk vo kvo ansd opxrsecyr ebzb, yfyjmwyyf uf hri nqgfyepizbq wgpia.");
        testData.put("THOR", "Bu hyx twula cw t iijmswez jwkr, avvkl sobzhj t zares, elblh gtyy wbszvw dwka izfhtwez mzfplfj tur tapfgbuu sbyrj, hmtvkpbx t tcdxuh fy wsrvl hf mocjx dvf ihgj uf.");
        impossibleTestData.clear();
        impossibleTestData.put("AKX", "Trfs sp a vlnq qehq trxt wxkop vooy vftdie cbncb wrbn okcbvpdbd, lrt S'j toilskg ilu deic fs gead ft sp avi allud yalv!");
    }

    @Test
    void findKey_withTestData_shouldReturnExpectedKeys() {
        for (String expectedKey : testData.keySet()) {
            List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(testData.get(expectedKey));
            List<Integer> keyLengthsWithinThreshold = getKeyLengthsWithinThreshold(keyLengthProbabilityResults);
            assertTrue(keyLengthsWithinThreshold.contains(expectedKey.length()));
        }
    }

    @Test
    void findKey_withImpossibleTestData_shouldReturnAllKeyLengths() {
        for (String expectedKey : impossibleTestData.keySet()) {
            List<KeyLengthProbabilityResult> keyLengthProbabilityResults = kasiskiAnalyzer.determineKeyLength(impossibleTestData.get(expectedKey));
            List<Integer> keyLengthsWithinThreshold = getKeyLengthsWithinThreshold(keyLengthProbabilityResults);
            assertEquals(impossibleTestData.get(expectedKey).length(), keyLengthsWithinThreshold.size());
            assertTrue(keyLengthsWithinThreshold.contains(expectedKey.length()));
        }
    }

    private List<Integer> getKeyLengthsWithinThreshold(List<KeyLengthProbabilityResult> results) {
        double maxValue = results.stream()
                .max(Comparator.comparingDouble(KeyLengthProbabilityResult::probability))
                .map(KeyLengthProbabilityResult::probability)
                .orElseThrow(() -> new RuntimeException("Expected at least one KeyLengthProbabilityResult!"));
        return results.stream()
                .filter(result -> result.probability() >= (maxValue - PASSWORD_CERTAINTY_THRESHOLD)
                        || result.probability() <= (maxValue + PASSWORD_CERTAINTY_THRESHOLD))
                .map(KeyLengthProbabilityResult::keylength)
                .toList();
    }
}