package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShowAllLocationsPage {

    private WebDriver driver;

    @FindBy(css = "#title")
    private WebElement titleText;

    @FindBy(css = "#addCircle")
    private WebElement addCircleIcon;

    @FindBy(css = "#addNew")
    private WebElement addNewButton;

    @FindBy(css = "#table")
    private WebElement locationsTable;

    @FindBy(css = "#next")
    private WebElement nextButton;

    @FindBy(css = "#previous")
    private WebElement previousButton;

    @FindBy(css = "#size")
    private WebElement sizeSelect;

    @FindBy(css = "#adminMenuBtn")
    private WebElement adminMenuBtn;



    public ShowAllLocationsPage(WebDriver driver) {
        this.driver = driver;
    }



    public WebElement getTitleText() {
        return titleText;
    }

    public WebElement getAddCircleIcon() {
        return addCircleIcon;
    }

    public WebElement getAddNewButton() {
        return addNewButton;
    }

    public WebElement getLocationsTable() {
        return locationsTable;
    }

    public WebElement getNextButton() {
        return nextButton;
    }

    public WebElement getPreviousButton() {
        return previousButton;
    }

    public WebElement getSizeSelect() {
        return sizeSelect;
    }


    public void ensureAddButtonIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addNewButton));
    }

    public void ensureNextButtonIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(nextButton));
    }

    public void ensurePreviousButtonIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(previousButton));
    }

    public void ensureTableIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(locationsTable));
    }

    public void ensureTableContainsRightData(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBe(By.className("editBtn"),1));
    }

}
