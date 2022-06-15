package ds.anosov.Tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import ds.anosov.Attachments.Attachments;
import ds.anosov.ConfigTest.CredentialsConfig;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static java.lang.String.format;

public class TestBase {
    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
        String https = "https://";
        String login = config.login();
        String password = config.password();
        String tailOfUrl = config.testUrl();
        String fullSelenoidUrl = format("%s%s%s%s", https, login, password, tailOfUrl);

        String propertyBaseUrl = System.getProperty("propertyBaseUrl", "http://demowebshop.tricentis.com");
        String propertyBaseUri = System.getProperty("propertyBaseUrl", "http://demowebshop.tricentis.com");
        String propertyBrowserSize = System.getProperty("propertyBrowserSize", "1920x1080");
        Configuration.baseUrl = propertyBaseUrl;
        RestAssured.baseURI = propertyBaseUri;
        Configuration.browserSize = propertyBrowserSize;
        Configuration.remote = fullSelenoidUrl;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void afterEach() {
        Attachments.screenshotAs("Last screenshot");
        Attachments.pageSource();
        Attachments.browserConsoleLogs();
        Attachments.addVideo();
        closeWebDriver();
    }
}
