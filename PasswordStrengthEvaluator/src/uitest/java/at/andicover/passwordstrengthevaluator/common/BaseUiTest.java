package at.andicover.passwordstrengthevaluator.common;

import at.andicover.passwordstrengthevaluator.PasswordStrengthEvaluatorWebApplication;
import at.andicover.passwordstrengthevaluator.login.UserService;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

/**
 * Base test for the Selenium E2E UI tests. Handles application startup, driver initialization and teardown.
 */
public abstract class BaseUiTest {

    private static final String BASE_URL = "http://127.0.0.1:8080/";
    protected static final long DEFAULT_TIMEOUT = 5L;
    protected static final LoginData TEST_USER = new LoginData("test", "test", "Admin");
    protected static final LoginData INVALID_USER = new LoginData("abc", "abc");

    @SuppressFBWarnings
    protected static WebDriver driver;
    @SuppressFBWarnings
    protected static WebDriverWait wait;

    private static ConfigurableApplicationContext context;

    protected BaseUiTest() {
        super();
    }

    @BeforeClass
    public static void setup() {
        startApplication();
        initWebDriver();
        initUser(TEST_USER);
    }

    @AfterClass
    public static void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
        deleteUser(TEST_USER);
        stopApplication();
    }

    private static void initWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    private static void startApplication() {
        context = SpringApplication.run(PasswordStrengthEvaluatorWebApplication.class);
    }

    private static void stopApplication() {
        SpringApplication.exit(context);
    }

    /**
     * @return the Base URL of the application.
     */
    protected String getBaseUrl() {
        return BASE_URL;
    }

    private static void initUser(@NonNull final LoginData user) {
        UserService.register(user);
    }

    private static void deleteUser(@NonNull final LoginData user) {
        UserService.delete(user);
    }

    protected String getValue(@NonNull final By by) {
        return driver.findElement(by).getAttribute("value");
    }

    protected String getProgressBarValue(@NonNull final By by) {
        return driver.findElement(by).getAttribute("aria-valuenow");
    }

    protected boolean getCheckboxStatus(@NonNull final By by) {
        return Boolean.parseBoolean(driver.findElement(by).getAttribute("aria-checked"));
    }

    protected void sendKeys(@NonNull final By by, @NonNull final String text) {
        driver.findElement(by).sendKeys(text);
        wait.until(ExpectedConditions.attributeContains(by, "value", text));
    }

    protected void click(@NonNull final By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    /**
     * Navigate to the specified page before each test.
     */
    @Before
    public abstract void navigateToPage();

    protected abstract String getPageUrl();
}