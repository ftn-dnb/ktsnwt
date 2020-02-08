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

    @FindBy(css = "#myReservationsBtn")
    private WebElement myReservationsButton;

    @FindBy(css = "#locationsBtn")
    private WebElement locationsButton;

    @FindBy(css = "#logoutBtn")
    private WebElement logoutButton;

    @FindBy(css = "body > app-root > app-home > div > mat-card > mat-card-actions > button")
    private WebElement seeMoreButton;

    public WebElement getMyReservationsButton() {
        return myReservationsButton;
    }

    public void setMyReservationsButton(WebElement myReservationsButton) {
        this.myReservationsButton = myReservationsButton;
    }

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

    public WebElement getLocationsButton(){ return  locationsButton;}

    public WebElement getLogoutButton() { return  logoutButton;};

    public WebElement getUserSettingsButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cdk-overlay-0")));
    }

    public WebElement getSeeMoreButton() { return seeMoreButton; }

    public void ensureLoginIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginButton));
    }

    public void ensureLoggedInPageIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(userMenuButton));
    }

    public WebElement getLogOut(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logoutBtn")));
    }

    public void ensureRegistrationButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(registrationButton));
    }
    public void ensureAdminMenuButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(adminMenuButton));
    }
    public void ensureMyReservationsButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(myReservationsButton));
    }

    public void ensureLocationButtonIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(locationsButton));
    }

    public void ensureUserMenuButtonIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(userMenuButton));
    }

}
