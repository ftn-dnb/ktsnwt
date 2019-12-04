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

import static org.testng.Assert.*;

public class LoginTest {

    private WebDriver browser;

    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    @Test
    public void testLogin() {
        // Go to login page
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        assertEquals(Constants.FRONTEND_APP_URL + "login", browser.getCurrentUrl());

        // Empty fields
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");

        // Only username
        loginPage.setUsername("asdfghjkl");
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");

        // Only password
        loginPage.setUsername("");
        loginPage.setPassword("643245");
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");

        // Bad credentials
        loginPage.setUsername("thisonedoesntexist");
        loginPage.setPassword("thisisrandompassword");
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");

        // Correct credentials
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();

        homePage.ensureLoggedInPageIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL);
    }
}
