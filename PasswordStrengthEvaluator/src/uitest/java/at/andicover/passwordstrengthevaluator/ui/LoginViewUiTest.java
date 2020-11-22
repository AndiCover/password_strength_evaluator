package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import at.andicover.passwordstrengthevaluator.model.LoginData;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.lang.NonNull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginViewUiTest extends BaseUiTest {

    @Override
    public void navigateToPage() {
        driver.get(getPageUrl());
        wait.until(ExpectedConditions.urlToBe(getPageUrl()));
    }

    @Override
    protected String getPageUrl() {
        return getBaseUrl() + LoginView.PATH;
    }

    @Test
    public void testUiComponentsVisible() {
        assertThat(driver.findElement(By.id(LoginView.UI_IDENTIFIER_USERNAME)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(LoginView.UI_IDENTIFIER_PASSWORD)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(LoginView.UI_IDENTIFIER_LOGIN)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(LoginView.UI_IDENTIFIER_BACK)).isDisplayed(), equalTo(true));
    }

    @Test
    public void testLoginSuccessful() {
        login(TEST_USER);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LoginView.UI_IDENTIFIER_LOGIN_SUCCESSFUL)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + AdminView.PATH));
    }

    private void login(@NonNull final LoginData user) {
        sendKeys(By.id(LoginView.UI_IDENTIFIER_USERNAME), user.getUsername());
        sendKeys(By.id(LoginView.UI_IDENTIFIER_PASSWORD), user.getPassword());
        click(By.id(LoginView.UI_IDENTIFIER_LOGIN));
    }

    @Test
    public void testLoginFailed() {
        login(INVALID_USER);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LoginView.UI_IDENTIFIER_LOGIN_FAILED)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }

    @Test
    public void testBackButton() {
        click(By.id(LoginView.UI_IDENTIFIER_BACK));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(LoginView.UI_IDENTIFIER_LOGIN)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + MainView.PATH));
    }
}