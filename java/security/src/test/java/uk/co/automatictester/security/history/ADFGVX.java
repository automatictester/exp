package uk.co.automatictester.security.history;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.ceil;

@Slf4j
public class ADFGVX {

    private final String[][] encryptionTable = new String[][]{
            {"N", "A", "1", "C", "3", "H"},
            {"8", "T", "B", "2", "O", "M"},
            {"E", "5", "W", "R", "P", "D"},
            {"4", "F", "6", "G", "7", "I"},
            {"9", "J", "0", "K", "L", "Q"},
            {"S", "U", "V", "X", "Y", "Z"},
    };
    private final String[] resultingLetters = new String[]{"A", "D", "F", "G", "V", "X"};
    private final String transpositionKey = "PRIVACY";

    @Test
    public void testEncryption() {
        String plaintext = "ATTACKAT1200AM";
        String fractionatedMessage = getFractionatedMessage(plaintext);
        String[][] table = fractionatedMessageToTable(fractionatedMessage);
        Map<Integer, Integer> transpositionMap = getTranspositionMap();
        String[][] transposedTable = transposeTable(table, transpositionMap);
        String cipherText = tableToCiphertext(transposedTable);
        log.info("Ciphertext: {}", cipherText);
    }

    /**
     * Converts:
     * ATTACKATONE => DGXGXGDGAADDDGXGADFAXA
     */
    private String getFractionatedMessage(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            String nextCharacter = plaintext.substring(i, i + 1);
            String cipherNext = getFractionatedCharacter(nextCharacter);
            ciphertext.append(cipherNext);
        }
        String ciphertextAsString = ciphertext.toString();
        log.debug("Fractionated message: {}", ciphertextAsString);
        return ciphertextAsString;
    }

    /**
     * Converts:
     * A => DG
     */
    private String getFractionatedCharacter(String character) {
        int tableRow = getTableRowFor(character);
        int tableCol = getTableColFor(character);
        String firstCharacter = resultingLetters[tableRow];
        String secondCharacter = resultingLetters[tableCol];
        return firstCharacter + secondCharacter;
    }

    private int getTableRowFor(String character) {
        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 5; col++) {
                if (encryptionTable[row][col].equals(character)) return row;
            }
        }
        throw new IllegalArgumentException("Character not found: " + character);
    }

    private int getTableColFor(String character) {
        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 5; col++) {
                if (encryptionTable[row][col].equals(character)) return col;
            }
        }
        throw new IllegalArgumentException("Character not found: " + character);
    }

    private String[][] fractionatedMessageToTable(String fractionatedMessage) {
        int rowCount = (int) ceil((float) fractionatedMessage.length() / (float) transpositionKey.length());
        log.debug("Calculated row count: {}", rowCount);
        int colCount = transpositionKey.length();

        String[][] table = new String[rowCount][colCount];
        int i = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (i < fractionatedMessage.length()) {
                    table[row][col] = fractionatedMessage.substring(i, i + 1);
                    i++;
                }
            }
        }
        String prettyTable = tableToPrettyString(table);
        log.debug("Table before transposition: \n{}", prettyTable);
        return table;
    }

    private String tableToPrettyString(String[][] table) {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[0].length; col++) {
                String cell = table[row][col];
                if (cell == null) {
                    output.append("_");
                } else {
                    output.append(cell);
                }
            }
            output.append("\n");
        }
        int lastCharacterIndex = output.length() - 1;
        output.deleteCharAt(lastCharacterIndex);
        return output.toString();
    }

    private Map<Integer, Integer> getTranspositionMap() {
        char[] keyCharacters = transpositionKey.toCharArray();
        Arrays.sort(keyCharacters);
        String sortedKey = new String(keyCharacters);
        log.debug("Sorted key: {}", sortedKey);

        Map<Integer, Integer> transpositionMap = new HashMap<>();
        int keyLength = keyCharacters.length;
        for (int positionBeforeSorting = 0; positionBeforeSorting < keyLength; positionBeforeSorting++) {
            String character = transpositionKey.substring(positionBeforeSorting, positionBeforeSorting + 1);
            int positionAfterSorting = sortedKey.indexOf(character);
            transpositionMap.put(positionBeforeSorting, positionAfterSorting);
        }
        return transpositionMap;
    }

    private String[][] transposeTable(String[][] table, Map<Integer, Integer> transpositionMap) {
        int rowCount = table.length;
        int colCount = table[0].length;
        String[][] transposedTable = new String[rowCount][colCount];
        for (int oldCol : transpositionMap.keySet()) {
            int newCol = transpositionMap.get(oldCol);
            for (int row = 0; row < rowCount; row++) {
                transposedTable[row][newCol] = table[row][oldCol];
            }
        }

        String prettyTransposedTable = tableToPrettyString(transposedTable);
        log.debug("Table before transposition: \n{}", prettyTransposedTable);
        return transposedTable;
    }

    private String tableToCiphertext(String[][] table) {
        StringBuilder ciphertext = new StringBuilder();
        int rowCount = table.length;
        int colCount = table[0].length;
        for (int col = 0; col < colCount; col++) {
            for (int row = 0; row < rowCount; row++) {
                String nextCharacter = table[row][col];
                if (nextCharacter != null) {
                    ciphertext.append(nextCharacter);
                }
            }
            ciphertext.append(" ");
        }
        return ciphertext.toString();
    }
}
