import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class RandomNumberGeneratorTest {

    RandomNumberGenerator generator = new RandomNumberGenerator();

    @Test(invocationCount = 100)
    public void testGetNumberReturnsOnlyIntegersInGivenRange() throws Exception {
        int x = generator.getNumber();
        assertThat(x, is(both(greaterThan(0)).and(lessThan(51))));
    }

    @Test
    public void testGetNumberReturnsAllIntegersInGivenRange() {
        Set<Integer> set = new HashSet<Integer>();

        for (int invocation = 1; invocation <= 50; invocation++) {
            for (int i = 0; i < 300; i++) {
                int x = generator.getNumber();
                set.add(x);
            }

            assertThat(set.contains(invocation), is(true));
            set.clear();
        }
    }
}
