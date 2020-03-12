package uk.co.automatictester.java9;

@Deprecated(               // Java 9 added 2 new methods:
        forRemoval = true, // whether the annotated element is subject to removal in a future version
        since = "1.2.0"    // version in which the annotated element became deprecated
)
public class DeprecatedTest {
}
