class RandomNumberGenerator {

    int getNumber() {
        return new Random().nextInt(50) + 1
    }
}
