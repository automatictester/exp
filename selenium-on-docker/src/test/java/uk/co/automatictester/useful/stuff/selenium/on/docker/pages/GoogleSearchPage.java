package uk.co.automatictester.useful.stuff.selenium.on.docker.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class GoogleSearchPage {

    private WebDriver driver;

    private static final By SEARCH_BOX = By.className("gsfi");

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageToLoad() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(SEARCH_BOX));
    }
}
