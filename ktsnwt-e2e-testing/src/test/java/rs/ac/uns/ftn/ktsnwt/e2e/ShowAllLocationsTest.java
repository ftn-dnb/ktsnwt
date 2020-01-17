package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.HomePage;
import rs.ac.uns.ftn.ktsnwt.pages.LoginPage;
import rs.ac.uns.ftn.ktsnwt.pages.RegistrationPage;
import rs.ac.uns.ftn.ktsnwt.pages.ShowAllLocationsPage;

import static org.testng.Assert.assertEquals;

public class ShowAllLocationsTest {


    private WebDriver browser;

    private LoginPage loginPage;
    private HomePage homePage;
    private ShowAllLocationsPage showAllLocationsPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage .class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        showAllLocationsPage = PageFactory.initElements(browser, ShowAllLocationsPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    public void gotoLocationsPage(){
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureLoggedInPageIsDisplayed();
        homePage.ensureAdminMenuButtonIsDisplayed();

        browser.navigate().to(Constants.FRONTEND_APP_URL + "locations");
        assertEquals(Constants.FRONTEND_APP_URL + "locations", browser.getCurrentUrl());

        showAllLocationsPage.ensureAddButtonIsDisplayed();
        showAllLocationsPage.ensureNextButtonIsDisplayed();
        showAllLocationsPage.ensureTableIsDisplayed();
        showAllLocationsPage.ensureTableContainsRightData();
    }

    @Test
    public void whenClickOnNextBtn_thenDisplayPreviousBtn(){
        this.gotoLocationsPage();
        showAllLocationsPage.getNextButton().click();
        showAllLocationsPage.ensurePreviousButtonIsDisplayed();
    }



}
