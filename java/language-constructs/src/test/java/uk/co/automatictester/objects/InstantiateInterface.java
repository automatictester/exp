package uk.co.automatictester.objects;

import org.testng.annotations.Test;

public class InstantiateInterface {

    interface Stuffable {
        void x();
    }

    @Test
    public void t() {
        Stuffable s = new Stuffable() {
            @Override
            public void x() {
            }
        };
    }
}
