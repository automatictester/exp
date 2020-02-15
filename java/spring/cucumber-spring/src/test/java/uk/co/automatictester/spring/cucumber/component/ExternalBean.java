package uk.co.automatictester.spring.cucumber.component;

public interface ExternalBean {

    default String getClassName() {
        return this.getClass().getSimpleName();
    }
}
