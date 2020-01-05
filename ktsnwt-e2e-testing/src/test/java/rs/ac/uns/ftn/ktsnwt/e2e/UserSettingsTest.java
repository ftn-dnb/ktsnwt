package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.HomePage;
import rs.ac.uns.ftn.ktsnwt.pages.LoginPage;
import rs.ac.uns.ftn.ktsnwt.pages.UserSettingsPage;

import static org.testng.Assert.*;

public class UserSettingsTest {

    private WebDriver browser;

    private HomePage homePage;
    private LoginPage loginPage;
    private UserSettingsPage settingsPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        homePage = PageFactory.initElements(browser, HomePage.class);
        settingsPage = PageFactory.initElements(browser, UserSettingsPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    private void gotoUserSettings() {
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("jane.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureLoggedInPageIsDisplayed();

        // homePage.getUserMenuButton().click();
        // homePage.getUserSettingsButton().click();

        // @NOTE: This is the quick way getting to the settings page
        // because its hard to navigate a angular menu in selenium
        browser.navigate().to(Constants.FRONTEND_APP_URL + "settings");

        assertEquals(Constants.FRONTEND_APP_URL + "settings", browser.getCurrentUrl());
        settingsPage.ensureDataIsLoaded();
        assertEquals(settingsPage.getUsernameHeader().getText(), "jane.doe");
    }

    @Test
    public void testImageChange() {
        this.gotoUserSettings();
        settingsPage.getImageUplaodButton().sendKeys(Constants.IMAGE_PATH);
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "settings");
        assertEquals(settingsPage.getToastMessage().getText(), Constants.USER_SETTINGS_IMAGE_CHANGE_SUCCESS);
    }

    @Test
    public void testChangeBasicInfoEmptyFields() {
        this.gotoUserSettings();

        // @HACK
        for (int i = 0; i < 15; i++) {
            settingsPage.getFirstNameInput().sendKeys(Keys.BACK_SPACE);
            settingsPage.getLastNameInput().sendKeys(Keys.BACK_SPACE);
            settingsPage.getEmailInput().sendKeys(Keys.BACK_SPACE);
        }

        assertFalse(settingsPage.getSaveButton().isEnabled());
        assertTrue(Boolean.parseBoolean(settingsPage.getFirstNameInput().getAttribute("aria-invalid")));
        assertTrue(Boolean.parseBoolean(settingsPage.getLastNameInput().getAttribute("aria-invalid")));
        assertTrue(Boolean.parseBoolean(settingsPage.getEmailInput().getAttribute("aria-invalid")));
    }

    @Test
    public void testChangeBasicInfoExistingEmail() {
        this.gotoUserSettings();

        settingsPage.setFirstName("New Jane");
        settingsPage.setLastName("New Doe");
        settingsPage.setEmail("john@doe.com");
        settingsPage.getSaveButton().click();

        assertEquals(settingsPage.getToastMessage().getText(), Constants.USER_SETTINGS_BACKEND_INFO_ERROR);
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "settings");
    }

    @Test
    public void testChangeBasicInfoInvalidEmail() {
        this.gotoUserSettings();

        settingsPage.setFirstName("New Jane");
        settingsPage.setLastName("New Doe");
        settingsPage.setEmail("this is invalid email");
        settingsPage.getSaveButton().click();

        assertFalse(settingsPage.getSaveButton().isEnabled());
        assertTrue(Boolean.parseBoolean(settingsPage.getEmailInput().getAttribute("aria-invalid")));
    }

    @Test
    public void testChangeBasicInfo() {
        this.gotoUserSettings();

        settingsPage.setFirstName("New Jane");
        settingsPage.setLastName("New Doe");
        settingsPage.setEmail("mynew@email.com");
        settingsPage.getSaveButton().click();

        assertEquals(settingsPage.getToastMessage().getText(), Constants.USER_SETTINGS_INFO_CHANGE_SUCCESS);
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "settings");
    }

    @Test
    public void testChangePasswordEmptyFields() {
        this.gotoUserSettings();

        settingsPage.getOldPasswordInput().click();
        settingsPage.getNewPasswordInput().click();
        settingsPage.getOldPasswordInput().click();
        assertFalse(settingsPage.getChangePasswordButton().isEnabled());
        assertTrue(Boolean.parseBoolean(settingsPage.getOldPasswordInput().getAttribute("aria-invalid")));
        assertTrue(Boolean.parseBoolean(settingsPage.getNewPasswordInput().getAttribute("aria-invalid")));
    }

    @Test
    public void testChangePasswordWrongOldPassword() {
        this.gotoUserSettings();

        settingsPage.getOldPasswordInput().sendKeys("12213123123");
        settingsPage.getNewPasswordInput().sendKeys("123");
        settingsPage.getChangePasswordButton().click();

        assertEquals(settingsPage.getToastMessage().getText(), Constants.USER_SETTINGS_BACKEND_PW_ERROR);
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "settings");
    }

    @Test
    public void testChangePassword() {
        this.gotoUserSettings();

        settingsPage.getOldPasswordInput().sendKeys("123");
        settingsPage.getNewPasswordInput().sendKeys("123");
        settingsPage.getChangePasswordButton().click();

        assertEquals(settingsPage.getToastMessage().getText(), Constants.USER_SETTINGS_PW_CHANGE_SUCCESS);
        assertEquals(browser.getCurrentUrl(), Constants.FRONTEND_APP_URL + "login");
    }
}
