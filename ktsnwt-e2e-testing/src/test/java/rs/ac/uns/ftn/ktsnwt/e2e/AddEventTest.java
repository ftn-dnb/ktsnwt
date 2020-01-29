package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.*;

import static org.testng.Assert.assertEquals;

public class AddEventTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private HomePage homePage;
    private ShowAllEventsPage showAllEventsPage;
    private AddEventPage addEventPage;


    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage .class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        showAllEventsPage = PageFactory.initElements(browser, ShowAllEventsPage.class);
        addEventPage = PageFactory.initElements(browser, AddEventPage.class);
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

        browser.navigate().to(browser.getCurrentUrl() + "events");
        showAllEventsPage.ensureAddBtnIsDisplayed();
        showAllEventsPage.getAddButton().click();
        addEventPage.ensureAllFieldsAreDisplayed();

    }

    @Test
    public void ensureAllIsDisplayed(){
        gotoLocationsPage();
    }

    @Test
    public void requireAllFields(){
        gotoLocationsPage();

        // hall is required
        addEventPage.fillAllInputs();
        addEventPage.fillType();
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.fillHall();

        // name required
        addEventPage.getNameInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.ensureNameErrIsDisplayed();
        addEventPage.setNameInputField("name");

        // start date is required
        addEventPage.getStartDateInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.setStartDateInputField("7/8/2020");

        // end date is required
        addEventPage.getEndDateInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.setEndDateInputField("7/10/2020");

        // purchsed limit is required
        addEventPage.getPurchaseLimitInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.ensurepurchsedLimitErrIsDisplayed();
        addEventPage.setPurchaseLimitInputField("7");

        // tickets per user is required
        addEventPage.getTicketsInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.ensureTicketsPerUserErrIsDisplayed();
        addEventPage.setTicketsInputField("7");

        // description is required
        addEventPage.getDescriptionInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.ensureDesriptionErrIsDisplayed();
        addEventPage.setDescriptionInputField("desc");

        // type is required
        String url = browser.getCurrentUrl();
        browser.navigate().to(url);    // reload
        addEventPage.fillAllInputs();
        addEventPage.fillHall();
        addEventPage.ensureButtonIsNotClickable();

        // picture is not required
        addEventPage.fillHall();
        addEventPage.ensureButtonIsClickable();

        // location is required
        addEventPage.getLocationInputField().sendKeys(Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE,Keys.BACK_SPACE);
        addEventPage.ensureButtonIsNotClickable();
        addEventPage.setLocationInputField("SPENS");
        addEventPage.setHallNameInputField();
        addEventPage.ensureButtonIsClickable();
    }

    @Test
    public void testStartDate(){
        this.gotoLocationsPage();
        addEventPage.fillAllInputs();
        addEventPage.fillHall();
        addEventPage.fillType();
        addEventPage.setStartDateInputField("1/2/2020");
        addEventPage.ensureStartErrIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();
    }

    @Test
    public void testEndDate(){
        this.gotoLocationsPage();
        addEventPage.fillAllInputs();
        addEventPage.fillHall();
        addEventPage.fillType();
        addEventPage.setEndDateInputField("1/9/2020");
        addEventPage.ensureEndErrBeforeTodayIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();
    }

    @Test
    public void testPurchasedLimit(){
        this.gotoLocationsPage();
        addEventPage.fillAllInputs();
        addEventPage.fillHall();
        addEventPage.fillType();

        // no letter
        addEventPage.setPurchaseLimitInputField("b");
        addEventPage.ensurepurchsedLimitErrIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();

        // no negative
        addEventPage.setPurchaseLimitInputField("-2");
        addEventPage.ensurepurchsedLimitNegativeErrIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();
    }

    @Test
    public void testTicketsPerUserLimit(){
        this.gotoLocationsPage();
        addEventPage.fillAllInputs();
        addEventPage.fillHall();
        addEventPage.fillType();

        // no letter
        addEventPage.setTicketsInputField("b");
        addEventPage.ensureTicketsPerUserErrIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();

        // no negative
        addEventPage.setTicketsInputField("-2");
        addEventPage.ensureTicketsPerUserErrNegativeIsDisplayed();
        addEventPage.ensureButtonIsNotClickable();
    }



}
