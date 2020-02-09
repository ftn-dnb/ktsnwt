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
import rs.ac.uns.ftn.ktsnwt.pages.ReservationPage;
import rs.ac.uns.ftn.ktsnwt.pages.ShowEventPage;

public class CancelEventTest {
    private WebDriver browser;

    private HomePage homePage;
    private LoginPage loginPage;
    private ShowEventPage showEventPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        showEventPage = PageFactory.initElements(browser, ShowEventPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    private void gotoShowEventPage() {
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureLoggedInPageIsDisplayed();
        homePage.getSeeMoreButton().click();

        showEventPage.ensureCancelButtonIsDisplayed();

    }

    @Test
    public void ensureAllDisplayed() {
        gotoShowEventPage();
    }

    @Test
    public void cancelEventDay() {
        gotoShowEventPage();
        showEventPage.getCancelButton().click();
    }
}
