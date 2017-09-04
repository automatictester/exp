class ResultsGenerator {

    RandomNumberGenerator numberGenerator = new RandomNumberGenerator()

    Set getResults() {
        Set results = new HashSet()

        while(results.size() < 6) {
            results.add(numberGenerator.getNumber())
        }

        results
    }
}
