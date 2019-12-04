package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    @FindBy(css = "#username-input")
    private WebElement usernameInputField;

    @FindBy(css = "#password-input")
    private WebElement passwordInputField;

    @FindBy(css = "#login-btn")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameInputField() {
        return usernameInputField;
    }

    public void setUsername(String username) {
        this.usernameInputField.clear();
        this.usernameInputField.sendKeys(username);
    }

    public WebElement getPasswordInputField() {
        return passwordInputField;
    }

    public void setPassword(String password) {
        this.passwordInputField.clear();
        this.passwordInputField.sendKeys(password);
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void ensureLoginBtnIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginButton));
    }
}
