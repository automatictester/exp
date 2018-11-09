package uk.co.automatictester.behavioral.observer.test;

import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.observer.Data;
import uk.co.automatictester.behavioral.observer.MinMaxCache;
import uk.co.automatictester.behavioral.observer.Observer;
import uk.co.automatictester.behavioral.observer.SumCache;

public class ObserverTest {

    @Test
    public void testObserver() {
        Data data = new Data();
        data.changeState();

        Observer minMaxCache = new MinMaxCache();
        Observer sumCache = new SumCache();

        data.attach(minMaxCache);
        data.attach(sumCache);

        data.changeState();

        data.detach(minMaxCache);
        data.detach(sumCache);

        data.changeState();
    }
}
