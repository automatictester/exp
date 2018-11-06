package uk.co.automatictester.searching;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BinarySearch {

    @Test(dataProvider = "input")
    public void binarySearchTest(int[] array, int value, int position) {

        int output = BinarySearcher.search(array, value);
        assertThat(output, is(equalTo(position)));
    }

    @DataProvider(name = "input")
    public Object[][] arrays() {
        return new Object[][]{
                {IntStream.rangeClosed(0, 51).toArray(), 4, 4},
                {IntStream.rangeClosed(0, 50).toArray(), 4, 4},
                {IntStream.rangeClosed(0, 10).toArray(), 11, -1},
                {IntStream.rangeClosed(0, 10).toArray(), -2, -1},
                {IntStream.rangeClosed(0, 99).toArray(), 99, 99},
        };
    }
}