package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.HomePage;
import rs.ac.uns.ftn.ktsnwt.pages.LoginPage;
import rs.ac.uns.ftn.ktsnwt.pages.MyReservationsPage;



public class MyReservationsTest {

    private WebDriver browser;

    private MyReservationsPage myReservationsPage;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        myReservationsPage = PageFactory.initElements(browser, MyReservationsPage.class);
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }


    public void gotoReservationsPage(){
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("john.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureMyReservationsButtonIsDisplayed();
        browser.navigate().to(browser.getCurrentUrl() + "my-reservations");

    }

    @Test
    public void ensureAllIsDisplayed(){
        this.gotoReservationsPage();
        myReservationsPage.ensureMoreThanXNumberOfButtonsAreDisplayed(0);
        myReservationsPage.ensureMoreThanXNumberOfRowsAreDisplayed(1);
    }


    // FIRST RESERVATION IN TABLE MUST BE SUCCESS, NEXT 2 ARE FAILURES:

    @Test
    public void testSuccess(){
        this.gotoReservationsPage();
        myReservationsPage.ensureMoreThanXNumberOfButtonsAreDisplayed(0);
        myReservationsPage.ensureMoreThanXNumberOfRowsAreDisplayed(1);
        int before = myReservationsPage.getNumberOfRows();

        myReservationsPage.ensureFirstButtonIsClickable();
        myReservationsPage.getFirstCancelButtonInTable().click();
        myReservationsPage.ensureCertainNumberOfRowsAreDisplayed(before-1);
    }

    @Test
    public void testPurchasedTimePassed(){
        this.gotoReservationsPage();
        myReservationsPage.ensureMoreThanXNumberOfButtonsAreDisplayed(1);
        myReservationsPage.ensureMoreThanXNumberOfRowsAreDisplayed(2);

        int before = myReservationsPage.getNumberOfRows();
        myReservationsPage.ensureSecondButtonIsClickable();
        myReservationsPage.getSecondCancelButtonInTable().click();
        myReservationsPage.ensureCertainNumberOfRowsAreDisplayed(before);
    }


}
