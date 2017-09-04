import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class RandomNumberGeneratorSpec extends Specification {

    @Shared
    def dg = new RandomNumberGenerator()

    @Shared
    Set set = new HashSet()

    @Unroll
    def "test all values in range 1-50"() {
        when:
        int x = dg.getNumber()

        then:
        x >= 1
        x <= 50

        where:
        counter << (1..100)
    }

    @Unroll
    def "test every integer from range 1-50 can be returned"() {
        when:
        for(int i = 0; i < 100; i++) {
            int x = dg.getNumber()
            set.add(x)
        }

        then:
        set.contains(counter)

        where:
        counter << (1..50)
    }
}
