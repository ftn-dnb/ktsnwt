package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddLocationPage {

    private WebDriver driver;

    @FindBy(css = "#mat-input-11")
    private WebElement autoCompleteInput;

    @FindBy(css = "#locationName")
    private WebElement locationNameInput;

    @FindBy(css ="#addLocationBtn")
    private WebElement addLocationBtn;

    @FindBy(css = "body > div.pac-container.pac-logo > div:nth-child(1)")
    private WebElement locationOption;

    @FindBy(css = "#mat-form-field-label-17")
    private WebElement labela;

    public AddLocationPage(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getAutoCompleteInputField(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mat-input-11")));
    }

    public WebElement getLabela(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mat-form-field-label-17")));
    }

    public WebElement getLocationOption(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div.pac-container.pac-logo > div:nth-child(1)")));
    }

    public WebElement getLocationNameInput(){
        return locationNameInput;
    }

    public WebElement getAddLocationBtn(){
        return addLocationBtn;
    }

    public void setLocationNameInput(String name){
        this.locationNameInput.clear();
        this.locationNameInput.sendKeys(name);
    }

    public void setAutoCompleteInput(String adress ){
        this.locationNameInput.clear();
        this.autoCompleteInput.sendKeys(adress);
    }

    public void ensureLocationBtnIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(addLocationBtn));
    }

    public void ensureAutoCompleteInputIsDisplayed(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(autoCompleteInput));
    }
}
