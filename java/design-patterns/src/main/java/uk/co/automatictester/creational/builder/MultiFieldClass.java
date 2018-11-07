package uk.co.automatictester.creational.builder;

public class MultiFieldClass {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    private MultiFieldClass(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return String.format("MultiFieldClass(a: %d, b: %d, c: %d, d: %d, e: %d)", a, b, c, d, e);
    }

    public static class Builder {
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;

        public Builder(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public MultiFieldClass.Builder withC(int c) {
            this.c = c;
            return this;
        }

        public MultiFieldClass.Builder withD(int d) {
            this.d = d;
            return this;
        }

        public MultiFieldClass.Builder withE(int e) {
            this.e = e;
            return this;
        }

        public MultiFieldClass build() {
            MultiFieldClass multiFieldClass = new MultiFieldClass(a, b);
            multiFieldClass.c = c;
            multiFieldClass.d = d;
            multiFieldClass.e = e;
            return multiFieldClass;
        }
    }
}
