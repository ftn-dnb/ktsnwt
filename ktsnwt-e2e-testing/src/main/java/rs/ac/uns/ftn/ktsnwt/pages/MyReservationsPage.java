package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MyReservationsPage {

    private WebDriver driver;

    public MyReservationsPage(WebDriver driver) {
        this.driver = driver;
    }

    // get buttons
    public WebElement getFirstCancelButtonInTable(){
        return driver.findElements(By.className("cancelBtn")).get(0);
    }
    public WebElement getSecondCancelButtonInTable(){
        return driver.findElements(By.className("cancelBtn")).get(1);
    }


    public int getNumberOfRows(){
        return driver.findElements(By.tagName("tr")).size();
    }

    public void ensureMoreThanXNumberOfButtonsAreDisplayed(int num){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".cancelBtn"),num));
    }

    public void ensureMoreThanXNumberOfRowsAreDisplayed(int num){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("tr"),num));
    }

    public void ensureFirstButtonIsClickable(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(getFirstCancelButtonInTable()));
    }

    public void ensureSecondButtonIsClickable(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(getSecondCancelButtonInTable()));
    }

    public void ensureCertainNumberOfButtonsAreDisplayed(int num){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".cancelBtn"),num));
    }

    public void ensureCertainNumberOfRowsAreDisplayed(int num) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.tagName("tr"),num));
    }



}
