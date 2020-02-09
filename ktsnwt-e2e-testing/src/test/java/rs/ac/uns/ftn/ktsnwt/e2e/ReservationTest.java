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

import java.util.concurrent.TimeUnit;

public class ReservationTest {

    private WebDriver browser;

    private HomePage homePage;
    private ReservationPage reservationPage;
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
        reservationPage = PageFactory.initElements(browser, ReservationPage.class);
        showEventPage = PageFactory.initElements(browser, ShowEventPage.class);
    }

    @AfterMethod
    public void shutdownSelenium() {
        browser.quit();
    }

    private void gotoReservationPage() {
        homePage.ensureLoginIsDisplayed();
        homePage.getLoginButton().click();
        loginPage.setUsername("john.doe");
        loginPage.setPassword("123");
        loginPage.getLoginButton().click();
        homePage.ensureLoggedInPageIsDisplayed();
        homePage.getSeeMoreButton().click();

        showEventPage.ensureShowEventIsDisplayed();
        showEventPage.getReserveButton().click();

        reservationPage.ensureLeftSectorIsClickable();
        reservationPage.ensureRightSectorIsClickable();
        reservationPage.ensureReservationButtonIsClickable();
    }

    @Test
    public void ensureAllIsDisplayed() {
        gotoReservationPage();
    }

    @Test
    public void testReservedSeats() {
        gotoReservationPage();
        reservationPage.getLeftSector().click();
        reservationPage.getReservedSeat().click();
        reservationPage.getReserveButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testNoSeatsSelected() {
        gotoReservationPage();
        reservationPage.getReserveButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testNumberOfSeats() {
        gotoReservationPage();
        reservationPage.getLeftSector().click();
        reservationPage.getUnreservedSeat().click();
        reservationPage.getUnreservedSeat1().click();
        reservationPage.getUnreservedSeat2().click();
        reservationPage.getReserveButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testReservation() {
        gotoReservationPage();
        reservationPage.getLeftSector().click();
        reservationPage.getUnreservedSeat().click();
        reservationPage.getReserveButton().click();
        browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        reservationPage.ensureLeftSectorIsClickable();
        reservationPage.getLeftSector().click();
        reservationPage.getUnreservedSeat().click();
        reservationPage.getReserveButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testFloor() {
        gotoReservationPage();
        reservationPage.ensureRightSectorIsClickable();
        reservationPage.getRightSector().click();
        reservationPage.ensureFloorFormVisible();
    }

    @Test
    public void testFloorInput() {
        gotoReservationPage();
        reservationPage.ensureRightSectorIsClickable();
        reservationPage.getRightSector().click();
        reservationPage.ensureFloorFormVisible();
        reservationPage.getNumSeatsInputField().sendKeys("rr4r66hh6");
        reservationPage.getReserveFloorButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testFloorInputNumberOfSeats() {
        gotoReservationPage();
        reservationPage.ensureRightSectorIsClickable();
        reservationPage.getRightSector().click();
        reservationPage.ensureFloorFormVisible();
        reservationPage.getNumSeatsInputField().sendKeys("4");
        reservationPage.ensureFloorFormVisible();
        reservationPage.getReserveFloorButton().click();
        reservationPage.ensureErrorMessageVisible();
    }

    @Test
    public void testFloorReservation() {
        gotoReservationPage();
        reservationPage.ensureRightSectorIsClickable();
        reservationPage.getRightSector().click();
        reservationPage.ensureFloorFormVisible();
        reservationPage.getNumSeatsInputField().sendKeys("1");
        reservationPage.getReserveFloorButton().click();
    }


}
