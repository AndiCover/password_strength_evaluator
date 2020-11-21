package at.andicover.passwordstrengthevaluator.common;

import at.andicover.passwordstrengthevaluator.PasswordStrengthEvaluatorWebApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class BaseUiTest {

    protected static WebDriver driver;
    private static ConfigurableApplicationContext context;

    protected BaseUiTest() {
        super();
    }

    @BeforeClass
    public static void setup() {
        startApplication();
        initWebDriver();
        navigateToLocalWebApplication();
    }

    @AfterClass
    public static void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
        stopApplication();
    }

    private static void initWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    private static void startApplication() {
        context = SpringApplication.run(PasswordStrengthEvaluatorWebApplication.class);
    }

    private static void stopApplication() {
        SpringApplication.exit(context);
    }

    protected static void navigateToLocalWebApplication() {
        driver.get("http://127.0.0.1:8080");
    }
}