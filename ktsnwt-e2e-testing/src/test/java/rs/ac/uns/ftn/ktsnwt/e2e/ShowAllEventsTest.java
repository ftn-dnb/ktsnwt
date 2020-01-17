package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.HomePage;
import rs.ac.uns.ftn.ktsnwt.pages.LoginPage;
import rs.ac.uns.ftn.ktsnwt.pages.ShowAllEventsPage;

public class ShowAllEventsTest {

    private WebDriver browser;
    private ShowAllEventsPage showAllEventsPage;
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
        showAllEventsPage = PageFactory.initElements(browser, ShowAllEventsPage.class);
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
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
    }

    @Test
    public void testAllIsDisplayed(){
        this.gotoEventsPage();
    }


}
