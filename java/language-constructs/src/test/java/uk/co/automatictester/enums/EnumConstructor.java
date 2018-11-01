package uk.co.automatictester.enums;

public enum EnumConstructor {
    A("a"), B("b");

    private String s;

    // enum constructor is either explicitly or implicitly private
    EnumConstructor(String s) {
        this.s = s;
    }
}
