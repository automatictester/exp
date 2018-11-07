package uk.co.automatictester.behavioral.state;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SuppressWarnings("SimplifiedTestNGAssertion")
public class LockableMapTest {

    @Test
    public void testUnlockedMap() {
        LockableMap<String, String> lockableMap = new LockableMap<>();
        lockableMap.put("a", "aaa");
        lockableMap.put("b", "bbb");

        String getA = lockableMap.get("a");
        assertTrue(getA.equals("aaa"));

        lockableMap.lock();
        String getB = lockableMap.get("b");
        assertTrue(getB.equals("bbb"));

        lockableMap.unlock();
        lockableMap.put("c", "ccc");
        String getC = lockableMap.get("c");
        assertTrue(getC.equals("ccc"));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testLockedMap() {
        LockableMap<String, String> lockableMap = new LockableMap<>();
        lockableMap.lock();
        lockableMap.put("d", "ddd");
    }
}
