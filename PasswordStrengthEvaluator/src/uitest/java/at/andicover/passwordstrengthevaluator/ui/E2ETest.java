package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class E2ETest extends BaseUiTest {

    @Override public void navigateToPage() {
        driver.get(getPageUrl());
        wait.until(ExpectedConditions.urlToBe(getPageUrl()));
    }

    @Override protected String getPageUrl() {
        return getBaseUrl() + MainView.PATH;
    }

    @Test
    public void testLoginLogout() {
        login();
        logout();
    }

    private void login() {
        click(By.id(MainView.UI_IDENTIFIER_LOGIN));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(MainView.UI_IDENTIFIER_LOGIN)));

        sendKeys(By.id(LoginView.UI_IDENTIFIER_USERNAME), TEST_USER.getUsername());
        sendKeys(By.id(LoginView.UI_IDENTIFIER_PASSWORD), TEST_USER.getPassword());
        click(By.id(LoginView.UI_IDENTIFIER_LOGIN));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LoginView.UI_IDENTIFIER_LOGIN_SUCCESSFUL)));

        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_HELLO)).getText(),
                equalTo("Hello " + TEST_USER.getName() + "!"));
    }

    private void logout() {
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_HELLO)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_LOGOUT)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_UPLOAD)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_TABLE)).isDisplayed(), equalTo(true));

        click(By.id(AdminView.UI_IDENTIFIER_LOGOUT));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AdminView.UI_IDENTIFIER_LOGOUT)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }
}