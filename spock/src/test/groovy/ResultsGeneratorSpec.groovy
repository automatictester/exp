import spock.lang.Specification
import spock.lang.Unroll

class ResultsGeneratorSpec extends Specification {

    @Unroll
    def "test result set has 6 items"() {
        given:
        RandomNumberGenerator randomNumberGenerator = Mock()
        ResultsGenerator resultsGenerator = new ResultsGenerator()
        resultsGenerator.numberGenerator = randomNumberGenerator

        and: "random number generator is mocked out and each invocation returns int in range 1-50"
        randomNumberGenerator.getNumber() >> { new Random().nextInt(50) + 1 }

        when:
        Set results = resultsGenerator.getResults()

        then:
        results.size() == 6

        where:
        counter << (1..100)
    }

    @Unroll
    def "test result generator calls getNumber at least 6 times"() {
        given:
        RandomNumberGenerator randomNumberGenerator = Mock()
        ResultsGenerator resultsGenerator = new ResultsGenerator()
        resultsGenerator.numberGenerator = randomNumberGenerator

        when:
        resultsGenerator.getResults()

        then: "random number generator is mocked out and each invocation returns int in range 1-50 AND it gets called at least 6 times"
        (6.._) * randomNumberGenerator.getNumber() >> { new Random().nextInt(50) + 1 }

        where:
        counter << (1..100)
    }
}
