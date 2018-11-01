import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.testng.annotations.Test;


public class CalcJunitTest {

    @Test
    public void testAdd() throws Exception {
        assertThat(Calc.add(1,2), is(3));
    }
}
