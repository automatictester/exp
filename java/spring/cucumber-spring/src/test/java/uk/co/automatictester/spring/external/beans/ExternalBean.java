package uk.co.automatictester.spring.external.beans;

public interface ExternalBean {

    default String getClassName() {
        return this.getClass().getSimpleName();
    }
}
