package uk.co.automatictester.sorting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SortingArrays {

    @Test(dataProvider = "input")
    public void bubbleSortTest(int[] array, String sorted) {

        IntArraySorter strategy = new BubbleIntArraySorter(array);
        strategy.sort();

        assertThat(Arrays.toString(array), is(equalTo(sorted)));
    }

    @DataProvider(name = "input")
    public Object[][] arrays() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 5}, "[1, 2, 3, 4, 5]"}, // best case
                {new int[]{1, 2, 5, 3, 4}, "[1, 2, 3, 4, 5]"},
                {new int[]{9, 5, 7, 1, 3}, "[1, 3, 5, 7, 9]"},
                {new int[]{9, 7, 5, 3, 1}, "[1, 3, 5, 7, 9]"}, // worst case
        };
    }
}
