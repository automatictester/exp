package uk.co.automatictester.useful.stuff.selenium.on.docker.test;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import uk.co.automatictester.useful.stuff.selenium.on.docker.pages.*;

import java.net.*;

public class MinimalSeleniumIT {

    private WebDriver driver;
    protected GoogleSearchPage google;

    public MinimalSeleniumIT() throws MalformedURLException {
        configureDriver();
        initialisePageObjects();
    }

    private void configureDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }

    private void initialisePageObjects() {
        google = new GoogleSearchPage(driver);
    }

    @Before
    public void navigateTo() {
        driver.get("https://www.google.co.uk");
    }

    @Test
    public void loadGoogle() {
        google.waitForPageToLoad();
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
