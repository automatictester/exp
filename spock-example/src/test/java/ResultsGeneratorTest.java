import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;
import java.util.Set;

import org.testng.annotations.Test;

public class ResultsGeneratorTest {

    RandomNumberGenerator generator = mock(RandomNumberGenerator.class);
    ResultsGenerator resultsGenerator = new ResultsGenerator();

    @Test(invocationCount = 100)
    public void testGetResultsReturnsSixItems() throws Exception {
        when(generator.getNumber()).thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1);
        resultsGenerator.setNumberGenerator(generator);

        Set results = resultsGenerator.getResults();
        assertThat(results.size(), is(6));
    }

    @Test(invocationCount = 100)
    public void testGetResultsCallsGetNumberAtLeastSixTimes() {
        when(generator.getNumber()).thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1)
                                   .thenReturn(new Random().nextInt(50) + 1);

        resultsGenerator.setNumberGenerator(generator);
        resultsGenerator.getResults();
        verify(generator, atLeast(6)).getNumber();
    }
}
