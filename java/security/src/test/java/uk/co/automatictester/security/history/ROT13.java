package uk.co.automatictester.security.history;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ROT13 {

    private static final int SHIFT = 13;
    private static final int LOWER_BOUND = 65;
    private static final int UPPER_BOUND = 90;

    @Test
    public void testEncrypt() {
        String input = "ABCDEFXYZ";
        String output = encrypt(input);
        String expected = "NOPQRSKLM";
        assertThat(expected, equalTo(output));

        String reversed = encrypt(output);
        assertThat(reversed, equalTo(input));
    }

    private String encrypt(String s) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            validate(c);
            char next = encryptChar(c);
            if (next > UPPER_BOUND) {
                int offset = next - UPPER_BOUND;
                next = (char) (LOWER_BOUND + offset - 1);
            }
            output.append(next);
        }
        return output.toString();
    }

    private char encryptChar(char c) {
        return (char) (c + SHIFT);
    }

    private void validate(char c) {
        if (c < LOWER_BOUND || c > UPPER_BOUND) throw new IllegalArgumentException();
    }
}
