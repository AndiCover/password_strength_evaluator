package at.andicover.passwordstrengthevaluator.ui;

import at.andicover.passwordstrengthevaluator.common.BaseUiTest;
import at.andicover.passwordstrengthevaluator.model.PasswordLength;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainViewUiTest extends BaseUiTest {

    private static final String WEAK_PASSWORD = "12345";
    private static final String BAD_PASSWORD = "kal12";
    private static final String MEDIUM_PASSWORD = "t4h8HJ(=";
    private static final String GOOD_PASSWORD = "JIfjds=fk30ßPÜ_j*?A";

    @Override
    public void navigateToPage() {
        driver.get(getPageUrl());
        wait.until(ExpectedConditions.urlToBe(getPageUrl()));
    }

    @Override
    protected String getPageUrl() {
        return getBaseUrl() + MainView.PATH;
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
        sendKeys(By.id(MainView.UI_IDENTIFIER_PASSWORD), WEAK_PASSWORD);
        wait.until(ExpectedConditions.attributeContains(By.id(MainView.UI_IDENTIFIER_ENTROPY), "value", "2.3"));
        assertThat(getProgressBarValue(By.id(MainView.UI_IDENTIFIER_SCORE)), equalTo("0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_PASSWORD_LENGTH)), equalTo(PasswordLength.SHORT.getName()));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_ENTROPY)), equalTo("2.3"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_UPPERCASE)), equalTo("0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_LOWERCASE)), equalTo("0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_NUMBERS)), equalTo("5"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_SYMBOLS)), equalTo("0"));
        assertThat(getCheckboxStatus(By.id(MainView.UI_IDENTIFIER_WEAK_PASSWORD)), equalTo(true));
    }

    @Test
    public void testBadPassword() {
        sendKeys(By.id(MainView.UI_IDENTIFIER_PASSWORD), BAD_PASSWORD);
        wait.until(ExpectedConditions.attributeContains(By.id(MainView.UI_IDENTIFIER_ENTROPY), "value", "2.3"));
        assertThat(getProgressBarValue(By.id(MainView.UI_IDENTIFIER_SCORE)), equalTo("0.09"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_PASSWORD_LENGTH)), equalTo(PasswordLength.SHORT.getName()));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_ENTROPY)), equalTo("2.3"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_UPPERCASE)), equalTo("0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_LOWERCASE)), equalTo("3"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_NUMBERS)), equalTo("2"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_SYMBOLS)), equalTo("0"));
        assertThat(getCheckboxStatus(By.id(MainView.UI_IDENTIFIER_WEAK_PASSWORD)), equalTo(false));
    }

    @Test
    public void testMediumPassword() {
        sendKeys(By.id(MainView.UI_IDENTIFIER_PASSWORD), MEDIUM_PASSWORD);
        wait.until(ExpectedConditions.attributeContains(By.id(MainView.UI_IDENTIFIER_ENTROPY), "value", "3.0"));
        assertThat(getProgressBarValue(By.id(MainView.UI_IDENTIFIER_SCORE)), equalTo("0.48"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_PASSWORD_LENGTH)), equalTo(PasswordLength.MEDIUM.getName()));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_ENTROPY)), equalTo("3.0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_UPPERCASE)), equalTo("2"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_LOWERCASE)), equalTo("2"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_NUMBERS)), equalTo("2"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_SYMBOLS)), equalTo("2"));
        assertThat(getCheckboxStatus(By.id(MainView.UI_IDENTIFIER_WEAK_PASSWORD)), equalTo(false));
    }

    @Test
    public void testStrongPassword() {
        sendKeys(By.id(MainView.UI_IDENTIFIER_PASSWORD), GOOD_PASSWORD);
        wait.until(ExpectedConditions.attributeContains(By.id(MainView.UI_IDENTIFIER_ENTROPY), "value", "4.0"));
        assertThat(getProgressBarValue(By.id(MainView.UI_IDENTIFIER_SCORE)), equalTo("1"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_PASSWORD_LENGTH)), equalTo(PasswordLength.LONG.getName()));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_ENTROPY)), equalTo("4.0"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_UPPERCASE)), equalTo("5"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_LOWERCASE)), equalTo("8"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_NUMBERS)), equalTo("2"));
        assertThat(getValue(By.id(MainView.UI_IDENTIFIER_SYMBOLS)), equalTo("4"));
        assertThat(getCheckboxStatus(By.id(MainView.UI_IDENTIFIER_WEAK_PASSWORD)), equalTo(false));
    }

    @Test
    public void testClickLogin() {
        click(By.id(MainView.UI_IDENTIFIER_LOGIN));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(MainView.UI_IDENTIFIER_LOGIN)));
        assertThat(driver.getCurrentUrl(), equalTo(getBaseUrl() + LoginView.PATH));
    }
}