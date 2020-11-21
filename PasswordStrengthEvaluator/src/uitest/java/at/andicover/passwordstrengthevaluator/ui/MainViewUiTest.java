package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainViewUiTest extends BaseUiTest {

    @Override
    public void navigateToPage() {
        driver.get(getBaseUrl() + MainView.PATH);
    }

    @Test
    public void testUiComponentsVisible() {
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_PASSWORD)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_SCORE)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_PASSWORD_LENGTH)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_ENTROPY)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_UPPERCASE)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_LOWERCASE)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_NUMBERS)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_SYMBOLS)).isDisplayed(), equalTo(true));
        assertThat(driver.findElement(By.id(MainView.UI_IDENTIFIER_WEAK_PASSWORD)).isDisplayed(), equalTo(true));
    }

    @Test
    public void testWeakPassword() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }

    @Test
    public void testBadPassword() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }

    @Test
    public void testMediumPassword() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }

    @Test
    public void testStrongPassword() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }

    @Test
    public void testClickLogin() {
        // TODO
        throw new NotImplementedException("Test not implemented");
    }
}