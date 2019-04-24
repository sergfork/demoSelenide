package someproduct;

import java.util.List;
import java.util.Optional;

public class Utils {
    public static Optional<String> searchOnThePage(List<String> foundTexts, String expectedDomain) {
        return foundTexts.stream().filter(l -> l.contains(expectedDomain)).findFirst();
    }
}
