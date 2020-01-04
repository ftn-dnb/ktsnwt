package rs.ac.uns.ftn.ktsnwt.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.ktsnwt.e2e.config.Constants;
import rs.ac.uns.ftn.ktsnwt.pages.HomePage;
import rs.ac.uns.ftn.ktsnwt.pages.RegistrationPage;

import static org.testng.Assert.*;

public class RegistrationTest {

    private WebDriver browser;

    private HomePage homePage;
    private RegistrationPage registrationPage;

    @BeforeMethod
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.navigate().to(Constants.FRONTEND_APP_URL);

        homePage = PageFactory.initElements(browser, HomePage.class);
        registrationPage = PageFactory.initElements(browser, RegistrationPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    private void gotoRegistrationPage() {
        homePage.ensureRegistrationButtonIsDisplayed();
        homePage.getRegistrationButton().click();
        assertEquals(Constants.FRONTEND_APP_URL + "register", browser.getCurrentUrl());
    }

    @Test
    public void testRegistrationEmptyFields() {
        this.gotoRegistrationPage();
        assertFalse(registrationPage.getRegisterButton().isEnabled());
        registrationPage.getFirstNameInputField().click();
        registrationPage.getLastNameInputField().click();
        registrationPage.getUsernameInputField().click();
        registrationPage.getEmailInputField().click();
        registrationPage.getPasswordInputField().click();
        registrationPage.getRepeatPasswordInputField().click();
        registrationPage.getFirstNameInputField().click();
        assertTrue(registrationPage.getFirstNameErrorMessage().isDisplayed());
        assertTrue(registrationPage.getLastNameErrorMessage().isDisplayed());
        assertTrue(registrationPage.getUsernameErrorMessage().isDisplayed());
        assertTrue(registrationPage.getEmailRequiredErrorMessage().isDisplayed());
        assertTrue(registrationPage.getPasswordErrorMessage().isDisplayed());
        assertTrue(registrationPage.getRepeatPasswordErrorMessage().isDisplayed());
        assertFalse(registrationPage.getRegisterButton().isEnabled());
    }

    @Test
    public void testRegistrationUsernameExists() {
        this.gotoRegistrationPage();

        registrationPage.setFirstName("First");
        registrationPage.setLastName("Last");
        registrationPage.setUsername("jane.doe");
        registrationPage.setEmail("janedoe@gmail.com");
        registrationPage.setPassword("123");
        registrationPage.setRepeatPassword("123");

        assertTrue(registrationPage.getRegisterButton().isEnabled());
        registrationPage.getRegisterButton().click();
        assertTrue(registrationPage.getToastMessage().isDisplayed());
        assertEquals(registrationPage.getToastMessage().getText(), Constants.REGISTRATION_BACKEND_ERROR);
    }

    @Test
    public void testRegistrationEmailExists() {
        this.gotoRegistrationPage();

        registrationPage.setFirstName("First");
        registrationPage.setLastName("Last");
        registrationPage.setUsername("jd");
        registrationPage.setEmail("jane@doe.com");
        registrationPage.setPassword("123");
        registrationPage.setRepeatPassword("123");

        assertTrue(registrationPage.getRegisterButton().isEnabled());
        registrationPage.getRegisterButton().click();
        assertTrue(registrationPage.getToastMessage().isDisplayed());
        assertEquals(registrationPage.getToastMessage().getText(), Constants.REGISTRATION_BACKEND_ERROR);
    }

    @Test
    public void testRegistrationInvalidEmail() {
        this.gotoRegistrationPage();

        registrationPage.setFirstName("First");
        registrationPage.setLastName("Last");
        registrationPage.setUsername("jd");
        registrationPage.setEmail("janedoecom");
        registrationPage.setPassword("123");
        registrationPage.setRepeatPassword("123");

        assertFalse(registrationPage.getRegisterButton().isEnabled());
        assertTrue(registrationPage.getEmailErrorMessage().isDisplayed());
    }

    @Test
    public void testRegistrationDifferentPasswords() {
        this.gotoRegistrationPage();

        registrationPage.setFirstName("First");
        registrationPage.setLastName("Last");
        registrationPage.setUsername("jd");
        registrationPage.setEmail("jane@doe.com");
        registrationPage.setPassword("123");
        registrationPage.setRepeatPassword("123123123123");

        assertTrue(registrationPage.getRegisterButton().isEnabled());
        registrationPage.getRegisterButton().click();
        assertTrue(registrationPage.getToastMessage().isDisplayed());
        assertEquals(registrationPage.getToastMessage().getText(), Constants.REGISTRATION_DIFFERENT_PASSWORDS);
    }

    @Test
    public void testRegistrationValidData() {
        this.gotoRegistrationPage();

        registrationPage.setFirstName("First");
        registrationPage.setLastName("Last");
        registrationPage.setUsername("jddd");
        registrationPage.setEmail("jddd@jd.com");
        registrationPage.setPassword("123");
        registrationPage.setRepeatPassword("123");

        assertTrue(registrationPage.getRegisterButton().isEnabled());
        registrationPage.getRegisterButton().click();
        assertTrue(registrationPage.getActivationSentScreen().isDisplayed());
    }
}
