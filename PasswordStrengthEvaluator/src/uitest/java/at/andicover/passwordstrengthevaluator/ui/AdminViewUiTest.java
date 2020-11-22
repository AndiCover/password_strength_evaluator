package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
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
    public void testNotLoggedIn() {
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_NOT_LOGGED_IN)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(AdminView.UI_IDENTIFIER_BACK)).isDisplayed(), equalTo(true));

        click(By.id(AdminView.UI_IDENTIFIER_BACK));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AdminView.UI_IDENTIFIER_BACK)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }
}