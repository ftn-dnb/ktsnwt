package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationPage {

    private WebDriver driver;

    public ReservationPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = "#x > g > rect:nth-child(4)")
    private WebElement leftSector;

    @FindBy(css = "#x > g > rect:nth-child(2)")
    private WebElement rightSector;

    @FindBy(css = "#x > g > g.left.focused > rect:nth-child(1)")
    private WebElement reservedSeat;

    @FindBy(css = "#x > g > g.left.focused > rect:nth-child(2)")
    private WebElement unreservedSeat;

    @FindBy(css = "#x > g > g.left.focused > rect:nth-child(3)")
    private WebElement unreservedSeat1;

    @FindBy(css = "#x > g > g.left.focused > rect:nth-child(4)")
    private WebElement unreservedSeat2;

    @FindBy(css = "body > app-root > app-reservations > button:nth-child(3)")
    private WebElement reserveButton;

    @FindBy(css = "#mat-dialog-0 > app-reservation-dialog > div.mat-dialog-actions > button:nth-child(2)")
    private WebElement reserveFloorButton;

    @FindBy(css = "#mat-input-2")
    private WebElement numSeatsInputField;

    @FindBy(css = "div.toast-message")
    private WebElement toastMessage;

    public void setNumberOfTickets(String numberOfTickets) {
        this.numSeatsInputField.clear();
        this.numSeatsInputField.sendKeys(numberOfTickets);
    }

    //public WebElement getLeftSector() {
    //    return leftSector;
    //}

    public WebElement getRightSector() {
        return rightSector;
    }

    public WebElement getReservedSeat() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#x > g > g.left.focused > rect:nth-child(1)")));
    }

    public WebElement getUnreservedSeat() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#x > g > g.left.focused > rect:nth-child(2)")));
    }

    public WebElement getReserveFloorButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mat-dialog-0 > app-reservation-dialog > div.mat-dialog-actions > button:nth-child(2)")));
    }

    public WebElement getUnreservedSeat1() {
        return unreservedSeat1;
    }

    public WebElement getUnreservedSeat2() {
        return unreservedSeat2;
    }

    public WebElement getReserveButton() {
        return reserveButton;
    }

    public WebElement getNumSeatsInputField() {
        return numSeatsInputField;
    }

    public WebElement getToastMessage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.toast-message")));
    }

    public void ensureErrorMessageVisible() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(toastMessage));
    }

    public void ensureReservationButtonIsClickable() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(reserveButton));
    }

    public void ensureLeftSectorIsClickable() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(leftSector));
    }

    public void ensureRightSectorIsClickable() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(rightSector));
    }

    public WebElement getLeftSector() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#x > g > rect:nth-child(4)")));
    }

    public void ensureFloorFormVisible() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(reserveFloorButton));
    }


}
