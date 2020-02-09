package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShowAllEventsPage {

    private WebDriver driver;

    public ShowAllEventsPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = ".btnAdd")
    private WebElement addButton;

    @FindBy(css = ".table")
    private WebElement table;

    @FindBy(css = ".previousBtn")
    private WebElement previousButton;

    @FindBy(css = ".nextBtn")
    private WebElement nextButton;

    @FindBy(css = ".btnEdit")
    private WebElement editBtn;

    public WebElement getAddButton() {
        return addButton;
    }

    public WebElement getTable() {
        return table;
    }

    public WebElement getEditBtn() {return  editBtn;};

    public void ensureAddBtnIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addButton));
    }


    public void ensureEditBtnIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(editBtn));
    }

    public void ensureTableIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(table));
    }

    public void ensureTableContainsRightData(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("btnArchive"), 0));
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("btnStats"), 0));
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("btnEdit"), 0));
        new WebDriverWait(driver, 10).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("btnDetails"), 0));
    }


}
