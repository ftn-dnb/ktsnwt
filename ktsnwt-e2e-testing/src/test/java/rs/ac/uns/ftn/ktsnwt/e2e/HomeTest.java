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

public class HomeTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage .class);
        homePage = PageFactory.initElements(browser, HomePage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }


    private void adminLogin(){
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
    }

    private void userLogin(){
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("john.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
    }

    @Test(expectedExceptions = TimeoutException.class)
    public void whenUserIsLogged_thenLocationsAreInvisible(){
        this.userLogin();
        homePage.ensureLoggedInPageIsDisplayed();
        homePage.ensureAdminMenuButtonIsDisplayed();
    }


}
