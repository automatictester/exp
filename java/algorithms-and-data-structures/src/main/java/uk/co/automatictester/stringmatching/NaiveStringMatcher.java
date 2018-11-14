package uk.co.automatictester.stringmatching;

import java.util.ArrayList;
import java.util.List;

public class NaiveStringMatcher {

    private NaiveStringMatcher() {
    }

    public static List<Integer> match(String pattern, String text) {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            boolean match = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    match = false;
                    break;
                }
            }
            if (match) positions.add(i);
        }

        return positions;
    }
}
