package uk.co.automatictester.generics;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class LessObviousDeclarations {

    @Test
    public void unusualDeclarations() {

        List<?> l1 = new ArrayList<>();
        List<? super String> l2 = new ArrayList<>();
        List<? extends String> l3 = new ArrayList<String>();

        // WON'T COMPILE - <T> IS NOT ALLOWED IN BOUND DEFINITION UNLESS A NAME OF A CLASS
        // List<T extends String> l4 = new ArrayList<String>();

        // WON'T COMPILE - RIGHT SIDE OF THE ASSIGNMENT CANNOT SPECIFY <T>, UNLESS DERIVED FROM CLASS AND NOT CONFLICTING WITH LEFT SIDE
        // List<?> l5 = new ArrayList<T>();

        // WON'T COMPILE - RIGHT SIDE OF THE ASSIGNMENT CANNOT SPECIFY <?>
        // List<?> l6 = new ArrayList<?>();
    }

    class CrazyA<T> { }

    // WON'T COMPILE - <?> IS NOT ALLOWED IN CLASS DEFINITION
    // class CrazyB<?> { }

    // WON'T COMPILE - EMPTY <> IS NOT ALLOWED IN CLASS DEFINITION
    // class CrazyC<> { }
}
