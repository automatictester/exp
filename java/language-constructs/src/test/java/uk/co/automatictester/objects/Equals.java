package uk.co.automatictester.objects;

public class Equals {

    class Animal {
        private int id;

        // equals() allows any business logic
        @Override
        public boolean equals(Object o) {
            if (o instanceof Animal) {
                return true;
            }
            Animal a = (Animal) o;
            return this.id == a.id;
        }

        public boolean equals(Animal a) {
            return this.id == a.id;
        }

        // two objects that return true for equals() must have the same hashCode()
        @Override
        public int hashCode() {
            return id;
        }
    }
}
