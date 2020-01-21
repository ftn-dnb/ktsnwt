package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;

    @FindBy(css = "#toolbar-login-btn")
    private WebElement loginButton;

    @FindBy(css = "#toolbar-registration-btn")
    private WebElement registrationButton;

    @FindBy(css = "#user-menu-btn")
    private WebElement userMenuButton;

    @FindBy(css = "#user-menu-settings-btn")
    private WebElement userSettingsButton;

    @FindBy(css = "#adminMenuBtn")
    private WebElement adminMenuButton;


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getAdminMenuButton() {
        return adminMenuButton;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getRegistrationButton() {
        return registrationButton;
    }

    public WebElement getUserMenuButton() {
        return userMenuButton;
    }

    public WebElement getUserSettingsButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-0")));
    }

    public void ensureLoginIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    public void ensureLoggedInPageIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(userMenuButton));
    }

    public void ensureRegistrationButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(registrationButton));
    }
    public void ensureAdminMenuButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(adminMenuButton));
    }
}
