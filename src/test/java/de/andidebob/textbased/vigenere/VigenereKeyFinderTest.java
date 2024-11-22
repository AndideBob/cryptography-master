package de.andidebob.textbased.vigenere;

import de.andidebob.textbased.language.EnglishLanguageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VigenereKeyFinderTest {

    private final HashMap<String, String> testData = new HashMap<>();
    private final VigenereKeyFinder vigenereKeyFinder = new VigenereKeyFinder(new EnglishLanguageModel());

    @BeforeEach
    void initializeTestData() {
        testData.clear();
        testData.put("AKX", "Trfs sp a vlnq qehq trxt wxkop vooy vftdie cbncb wrbn okcbvpdbd, lrt S'j toilskg ilu deic fs gead ft sp avi allud yalv!");
        testData.put("BROKEN", "Evgzmgf kvo hvtkoxx evdpvi bg kverqfi, hri pizznvro tcxxvolsn tybpwxk vo kvo ansd opxrsecyr ebzb, yfyjmwyyf uf hri nqgfyepizbq wgpia.");
        testData.put("THOR", "Bu hyx twula cw t iijmswez jwkr, avvkl sobzhj t zares, elblh gtyy wbszvw dwka izfhtwez mzfplfj tur tapfgbuu sbyrj, hmtvkpbx t tcdxuh fy wsrvl hf mocjx dvf ihgj uf.");
    }

    @Test
    void findKey_shouldReturnExpectedKeys() {
        for (String expectedKey : testData.keySet()) {
            String foundKey = vigenereKeyFinder.findKey(testData.get(expectedKey), expectedKey.length());
            assertEquals(expectedKey, foundKey);
        }
    }
}