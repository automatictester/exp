package uk.co.automatictester.behavioral.state.test;

import org.testng.annotations.Test;
import uk.co.automatictester.behavioral.state.LockableMap;

import static org.testng.Assert.assertEquals;

public class LockableMapTest {

    @Test
    public void testUnlockedMap() {
        LockableMap<String, String> lockableMap = new LockableMap<>();
        lockableMap.put("a", "aaa");
        lockableMap.put("b", "bbb");

        String getA = lockableMap.get("a");
        assertEquals("aaa", getA);

        lockableMap.lock();
        String getB = lockableMap.get("b");
        assertEquals("bbb", getB);

        lockableMap.unlock();
        lockableMap.put("c", "ccc");
        String getC = lockableMap.get("c");
        assertEquals("ccc", getC);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testLockedMap() {
        LockableMap<String, String> lockableMap = new LockableMap<>();
        lockableMap.lock();
        lockableMap.put("d", "ddd");
    }
}
