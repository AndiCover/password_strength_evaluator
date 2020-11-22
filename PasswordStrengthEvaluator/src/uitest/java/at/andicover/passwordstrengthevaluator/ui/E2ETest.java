package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import org.apache.commons.lang3.NotImplementedException;
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
    public void testFileUpload() {
        login();
        uploadFile();
        //TODO asserts

        logout();
        throw new NotImplementedException("Test not implemented");
    }

    private void login() {
        driver.findElement(By.id(MainView.UI_IDENTIFIER_LOGIN)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(MainView.UI_IDENTIFIER_LOGIN)));

        sendKeys(By.id(LoginView.UI_IDENTIFIER_USERNAME), TEST_USER.getUsername());
        sendKeys(By.id(LoginView.UI_IDENTIFIER_PASSWORD), TEST_USER.getPassword());
        driver.findElement(By.id(LoginView.UI_IDENTIFIER_LOGIN)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(LoginView.UI_IDENTIFIER_LOGIN_SUCCESSFUL)));

        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_HELLO)).getText(),
                equalTo("Hello " + TEST_USER.getName() + "!"));
    }

    private void logout() {
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_HELLO)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_LOGOUT)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_UPLOAD)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_TABLE)).isDisplayed(), equalTo(true));

        driver.findElement(By.id(AdminView.UI_IDENTIFIER_LOGOUT)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AdminView.UI_IDENTIFIER_LOGOUT)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }

    private void uploadFile() {
        //TODO
    }
}