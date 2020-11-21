package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import at.andicover.passwordstrengthevaluator.model.User;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AdminViewUiTest extends BaseUiTest {

    @Override
    public void navigateToPage() {
        driver.get(getPageUrl());
        wait.until(ExpectedConditions.urlToBe(getPageUrl()));
    }

    @Override
    protected String getPageUrl() {
        return getBaseUrl() + AdminView.PATH;
    }

    @Test
    public void testLogout() {
        VaadinSession.getCurrent().setAttribute(User.SESSION_ATTRIBUTE, TEST_USER); //TODO
        navigateToPage();

        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_HELLO)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_LOGOUT)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_UPLOAD)).isDisplayed(), equalTo(true));

        driver.findElement(By.id(AdminView.UI_IDENTIFIER_LOGOUT)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AdminView.UI_IDENTIFIER_LOGOUT)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }

    @Test
    public void testNotLoggedIn() {
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_NOT_LOGGED_IN)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_BACK)).isDisplayed(), equalTo(true));

        driver.findElement(By.id(AdminView.UI_IDENTIFIER_BACK)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AdminView.UI_IDENTIFIER_BACK)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }

    @Test
    public void testFileUpload() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }
}