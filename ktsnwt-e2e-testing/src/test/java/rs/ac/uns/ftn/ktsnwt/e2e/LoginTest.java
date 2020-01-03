package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.NoSuchElementException;
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

    private void gotoLoginPage() {
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        assertEquals(Constants.FRONTEND_APP_URL + "login", browser.getCurrentUrl());
    }

    @Test
    public void testLoginEmptyFields() {
        gotoLoginPage();

        loginPage.getUsernameInputField().click();
        loginPage.getPasswordInputField().click();
        loginPage.getUsernameInputField().click();
        assertTrue(loginPage.getPasswordErrorMessage().isDisplayed());
        assertTrue(loginPage.getUsernameErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());
    }

    @Test
    public void testLoginOnlyUsername() {
        this.gotoLoginPage();
        loginPage.getUsernameInputField().click();
        loginPage.setUsername("random-username");
        loginPage.getPasswordInputField().click();
        loginPage.getUsernameInputField().click();
        assertTrue(loginPage.getPasswordErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());

        try {
            assertFalse(loginPage.getUsernameErrorMessage().isDisplayed());
        } catch(NoSuchElementException e) {
            assertTrue(true); // @HACK: username error message is not displayed
        }
    }

    @Test
    public void testLoginOnlyPassword() {
        this.gotoLoginPage();

        loginPage.getUsernameInputField().click();
        loginPage.getPasswordInputField().click();
        loginPage.setPassword("643245");
        assertTrue(loginPage.getUsernameErrorMessage().isDisplayed());
        assertFalse(loginPage.getLoginButton().isEnabled());

        try {
            assertFalse(loginPage.getPasswordErrorMessage().isDisplayed());
        } catch(NoSuchElementException e) {
            assertTrue(true); // @HACK
        }
    }

    @Test
    public void testLoginBadCredentials() {
        this.gotoLoginPage();

        loginPage.setUsername("thisonedoesntexist");
        loginPage.setPassword("thisisrandompassword");
        assertTrue(loginPage.getLoginButton().isEnabled());
        loginPage.getLoginButton().click();
        loginPage.ensureLoginBtnIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");
    }

    @Test
    public void testLoginCorrectCredentials() {
        this.gotoLoginPage();

        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        assertTrue(loginPage.getLoginButton().isEnabled());
        loginPage.getLoginButton().click();

        homePage.ensureLoggedInPageIsDisplayed();
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL);
    }
}
