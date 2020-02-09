package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.*;

public class EditEventTest {

    private WebDriver browser;
    private ShowAllEventsPage showAllEventsPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private EditEventPage editEventPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        editEventPage = PageFactory.initElements(browser, EditEventPage.class);
        showAllEventsPage = PageFactory.initElements(browser, ShowAllEventsPage.class);

    }

    public void gotoEventsPage(){
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureAdminMenuButtonIsDisplayed();
        browser.navigate().to(browser.getCurrentUrl() + "events");
        showAllEventsPage.ensureAddBtnIsDisplayed();
        showAllEventsPage.ensureTableIsDisplayed();
        showAllEventsPage.ensureTableContainsRightData();
        showAllEventsPage.ensureEditBtnIsDisplayed();
        showAllEventsPage.getEditBtn().click();
    }

    @Test
    public void pocetniTest(){
        gotoEventsPage();

        editEventPage.getTicketsPerUserInput().clear();
        editEventPage.getTicketsPerUserInputEmpty().isDisplayed();
    }


    @Test
    public void drugi(){
        gotoEventsPage();

        editEventPage.getPurchaseLimitInput().clear();
        editEventPage.getPurchaseLimitInputEmpty().isDisplayed();
    }

    @Test
    public void treci(){
        gotoEventsPage();

        editEventPage.getDescriptionInput().clear();
        editEventPage.getDescriptionInput().click();
        editEventPage.getDescriptionInputEmpty().isDisplayed();
    }

    @Test
    public void cetvrti(){
        gotoEventsPage();
        editEventPage.getEditBtnButton().isEnabled();
    }
}
