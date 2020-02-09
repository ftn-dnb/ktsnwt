package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShowEventPage {

    private WebDriver driver;

    @FindBy(css = "#reserve")
    private WebElement reserveButton;

    @FindBy(css = "#cancelButton")
    private WebElement cancelButton;

    public ShowEventPage(WebDriver driver) { this.driver = driver; }

    public void ensureShowEventIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(reserveButton));
    }

    public void ensureCancelButtonIsDisplayed() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(cancelButton));
    }

    public WebElement getReserveButton() {
        return reserveButton;
    }

    public WebElement getCancelButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cancelButton")));
    }
}
