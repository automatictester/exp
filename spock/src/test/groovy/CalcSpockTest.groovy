import spock.lang.Specification
import spock.lang.Unroll

class CalcSpockTest extends Specification {

    def "Should add 2 values correctly"() {

        given:
        int a = 1;
        and:
        int b = 2;

        when:
        int x = Calc.add(a, b);

        then:
        x == 3;
    }

    @Unroll
    def "Should add 2 values correctly - data driven testing: #a + #b == #x"() {

        expect:
        Calc.add(a, b) == x

        where:
        a | b | x
        1 | 1 | 2
        1 | 2 | 3
        1 | 0 | 1
        2 | 2 | 4
    }

    def "Should add 2 values - simplistic form"() {

        expect:
        Calc.add(4, 5) == 9;
    }
}
