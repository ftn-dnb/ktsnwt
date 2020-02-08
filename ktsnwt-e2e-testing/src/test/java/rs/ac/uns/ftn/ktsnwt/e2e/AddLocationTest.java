package rs.ac.uns.ftn.ktsnwt.e2e;

import jdk.jfr.Timespan;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.*;

public class AddLocationTest {

    private WebDriver browser;

    private HomePage homePage;
    private LoginPage loginPage;
    private ShowAllLocationsPage locationsPage;
    private AddLocationPage addLocationPage;


    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        locationsPage = PageFactory.initElements(browser, ShowAllLocationsPage.class);
        addLocationPage = PageFactory.initElements(browser, AddLocationPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    public void goToAddLocationPage(){

        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureAdminMenuButtonIsDisplayed();
        homePage.getAdminMenuButton().click();
        homePage.ensureLocationButtonIsDisplayed();
        homePage.getLocationsButton().click();
        locationsPage.ensureAddButtonIsDisplayed();
        locationsPage.getAddNewButton().click();
        addLocationPage.ensureLocationBtnIsDisplayed();
        addLocationPage.ensureAutoCompleteInputIsDisplayed();


    }

    @Test
    public void prviTest(){
        goToAddLocationPage();

        addLocationPage.getAutoCompleteInputField().sendKeys("Beograd");
        addLocationPage.getLabela().sendKeys("Beograd");
        addLocationPage.getLocationOption().click();
        addLocationPage.setLocationNameInput("BBBBB");
        addLocationPage.getAddLocationBtn().click();


    }

    @Test
    public void drugiTest(){
        goToAddLocationPage();
    }

}
