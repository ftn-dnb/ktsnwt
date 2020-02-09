package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditEventPage {

    private WebDriver driver;

    public EditEventPage(WebDriver driver){ this.driver = driver;}

    @FindBy( css = "#purchase-limit-input")
    private WebElement purchaseLimitInput;

    @FindBy( css = "#tickets-per-user-input")
    private WebElement ticketsPerUserInput;

    @FindBy( css = "#description-input")
    private WebElement descriptionInput;

    @FindBy( css = "#edit-btn")
    private WebElement editBtn;

    @FindBy( css = "small#purchase-error-msg-req")
    private WebElement purchaseLimitInputEmpty;

    @FindBy( css = "small#number-error-msg-req")
    private WebElement ticketsPerUserInputEmpty;

    @FindBy( css = "small#description-error-msg-req")
    private WebElement descriptionInputEmpty;


    public WebElement getDescriptionInputEmpty() {
        return descriptionInputEmpty;
    }

    public WebElement getPurchaseLimitInputEmpty() {
        return purchaseLimitInputEmpty;
    }

    public WebElement getTicketsPerUserInputEmpty() {
        return ticketsPerUserInputEmpty;
    }

    public WebElement getEditBtnButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#edit-btn")));
    }

    public WebElement getDescriptionInput() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#description-input")));
    }

    public WebElement getTicketsPerUserInput() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tickets-per-user-input")));
    }

    public WebElement getPurchaseLimitInput() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#purchase-limit-input")));
    }

}
