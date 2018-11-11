package uk.co.automatictester.sorting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ArraySortTest {

    @Test(dataProvider = "input")
    public void bubbleSortTest(int[] array, String sorted) {

        IntArraySorter strategy = new IntArrayBubbleSorter(array);
        strategy.sort();

        assertThat(Arrays.toString(array), is(equalTo(sorted)));
    }

    @Test(dataProvider = "input")
    public void selectionSortTest(int[] array, String sorted) {

        IntArraySorter strategy = new IntArraySelectionSorter(array);
        strategy.sort();

        assertThat(Arrays.toString(array), is(equalTo(sorted)));
    }

    @Test(dataProvider = "input")
    public void insertionSortTest(int[] array, String sorted) {

        IntArraySorter strategy = new IntArrayInsertionSorter(array);
        strategy.sort();

        assertThat(Arrays.toString(array), is(equalTo(sorted)));
    }

    @DataProvider(name = "input")
    public Object[][] arrays() {
        return new Object[][]{
                {new int[]{9, 5, 7, 1, 3}, "[1, 3, 5, 7, 9]"},
                {new int[]{1, 2, 3, 4, 5}, "[1, 2, 3, 4, 5]"},
                {new int[]{1, 3, 5, 7, 8}, "[1, 3, 5, 7, 8]"},
                {new int[]{1, 2, 5, 3, 4}, "[1, 2, 3, 4, 5]"},
                {new int[]{9, 7, 5, 3, 1}, "[1, 3, 5, 7, 9]"},
                {new int[]{6, 5, 3, 1, 8, 7, 2, 4}, "[1, 2, 3, 4, 5, 6, 7, 8]"},
                {new int[]{38, 27, 43, 3, 9, 82, 10}, "[3, 9, 10, 27, 38, 43, 82]"},
        };
    }
}
