package rs.ac.uns.ftn.ktsnwt.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEventPage {

    private WebDriver driver;

    public AddEventPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(css = "#name-input")
    private WebElement nameInputField;

    @FindBy(css = "#name-error-msg-req")
    private WebElement nameRequestedErrorMsg;

    @FindBy(css = "#start-date-input")
    private WebElement startDateInputField;

    @FindBy(css = "#start-date-error-msg")
    private WebElement startDateErrorMsg;

    @FindBy(css = "#end-date-input")
    private WebElement endDateInputField;

    @FindBy(css = "#end-date-error-msg")
    private WebElement endDateErrorMsg;

    @FindBy(css = "#end-date-error-msg-today")
    private WebElement endDateBeforeTodayErrorMsg;

    @FindBy(css = "#purchase-limit-input")
    private WebElement purchaseLimitInputField;

    @FindBy(css = "#purchase-error-msg-req")
    private WebElement purchaseLimitErrorMsg;

    @FindBy(css = "#purchase-error-msg-negative")
    private WebElement purchaseLimitNegativeErrorMsg;

    @FindBy(css = "#tickets-per-user-input")
    private WebElement ticketsInputField;

    @FindBy(css = "#number-error-msg-req")
    private WebElement ticketsNumberErrorMsg;

    @FindBy(css = "#number-error-msg-negative")
    private WebElement ticketsNumberNegativeErrorMsg;

    @FindBy(css = "#type-input")
    private WebElement typeInputField;

    @FindBy(css = "#type-error-msg-req")
    private WebElement typeErrorMsg;

    @FindBy(css = "#location-input")
    private WebElement locationInputField;

    @FindBy(css = "#hall-id-input")
    private WebElement hallNameInputField;

    @FindBy(css = "#hall-id-error-msg-req")
    private WebElement hallReqErrorMsg;

    @FindBy(css = "#description-input")
    private WebElement descriptionInputField;

    @FindBy(css = "#description-error-msg-req")
    private WebElement descriptionErrorMsg;

    @FindBy(css = "#upload")
    private WebElement uploadPictureInput;

    @FindBy(css = ".submit-button")
    private WebElement addButton;

    public WebElement getNameInputField() {
        return nameInputField;
    }

    public WebElement getNameRequestedErrorMsg() {
        return nameRequestedErrorMsg;
    }

    public WebElement getStartDateInputField() {
        return startDateInputField;
    }

    public WebElement getStartDateErrorMsg() {
        return startDateErrorMsg;
    }

    public WebElement getEndDateInputField() {
        return endDateInputField;
    }

    public WebElement getEndDateErrorMsg() {
        return endDateErrorMsg;
    }

    public WebElement getEndDateBeforeTodayErrorMsg() {
        return endDateBeforeTodayErrorMsg;
    }

    public WebElement getPurchaseLimitInputField() {
        return purchaseLimitInputField;
    }

    public WebElement getPurchaseLimitErrorMsg() {
        return purchaseLimitErrorMsg;
    }

    public WebElement getPurchaseLimitNegativeErrorMsg() {
        return purchaseLimitNegativeErrorMsg;
    }

    public WebElement getTicketsInputField() {
        return ticketsInputField;
    }

    public WebElement getTicketsNumberErrorMsg() {
        return ticketsNumberErrorMsg;
    }

    public WebElement getTicketsNumberNegativeErrorMsg() {
        return ticketsNumberNegativeErrorMsg;
    }

    public WebElement getTypeInputField() {
        return typeInputField;
    }

    public WebElement getTypeErrorMsg() {
        return typeErrorMsg;
    }

    public WebElement getLocationInputField() {
        return locationInputField;
    }

    public WebElement getHallNameInputField() {
        return hallNameInputField;
    }

    public WebElement getHallReqErrorMsg() {
        return hallReqErrorMsg;
    }

    public WebElement getDescriptionInputField() {
        return descriptionInputField;
    }

    public WebElement getDescriptionErrorMsg() {
        return descriptionErrorMsg;
    }

    public WebElement getUploadPictureInput() {
        return uploadPictureInput;
    }

    public WebElement getAddButton() {
        return addButton;
    }


    public void setNameInputField(String nameInputField) {
        this.nameInputField.clear();
        this.nameInputField.sendKeys(nameInputField);
    }


    public void setStartDateInputField(String startDateInputField) {
        this.startDateInputField.clear();
        this.startDateInputField.sendKeys(startDateInputField);
    }

    public void setEndDateInputField(String endDateInputField) {
        this.endDateInputField.clear();
        this.endDateInputField.sendKeys(endDateInputField);
    }

    public void setPurchaseLimitInputField(String purchaseLimitInputField) {
        this.purchaseLimitInputField.clear();
        this.purchaseLimitInputField.sendKeys(purchaseLimitInputField);
    }


    public void setTicketsInputField(String ticketsInputField) {
        this.ticketsInputField.clear();
        this.ticketsInputField.sendKeys(ticketsInputField);
    }

    public void setTypeInputField() {
        this.typeInputField.click();
        new WebDriverWait(driver,20).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("type_option"),0));
        driver.findElement(By.className("type_option")).click();
    }

    public void setLocationInputField(String locationInputField) {
        this.locationInputField.clear();
        this.locationInputField.sendKeys(locationInputField);
    }

    public void setHallNameInputField() {
        this.hallNameInputField.click();
        new WebDriverWait(driver,20).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("hall_option"),0));
        driver.findElement(By.className("hall_option")).click();
    }


    public void setDescriptionInputField(String descriptionInputField) {
        this.descriptionInputField.clear();
        this.descriptionInputField.sendKeys(descriptionInputField);
    }

    public void setUploadPictureInput(String uploadPictureInput) {
        this.uploadPictureInput.sendKeys(uploadPictureInput);
    }

    public void ensureAllFieldsAreDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("input")));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(typeInputField));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(hallNameInputField));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(descriptionInputField));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(addButton));

    }

    public void ensureButtonIsNotClickable(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(addButton));
        new WebDriverWait(driver, 10).until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(addButton)));
    }

    public void ensureButtonIsClickable(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(addButton));
    }

    public void fillAllInputs(){
        setNameInputField("name");
        setStartDateInputField("7/8/2020");
        setEndDateInputField("7/10/2020");
        setPurchaseLimitInputField("1");
        setTicketsInputField("2");
        setLocationInputField("SPENS");
        setDescriptionInputField("desc");
    }


    public void fillType(){
        setTypeInputField();
    }

    public void fillHall(){
        setHallNameInputField();
    }
    public void ensureNameErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(nameRequestedErrorMsg));
    }
    public void ensureStartErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(startDateErrorMsg));
    }
    public void ensureEndErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(endDateErrorMsg));
    }
    public void ensureEndErrBeforeTodayIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(endDateBeforeTodayErrorMsg));
    }
    public void ensurepurchsedLimitErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(purchaseLimitErrorMsg));
    }
    public void ensurepurchsedLimitNegativeErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(purchaseLimitNegativeErrorMsg));
    }
    public void ensureTicketsPerUserErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(ticketsNumberErrorMsg));
    }
    public void ensureTicketsPerUserErrNegativeIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(ticketsNumberNegativeErrorMsg));
    }

    public void ensureTypeErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(typeErrorMsg));
    }
    public void ensureHallErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(hallReqErrorMsg));
    }

    public void ensureDesriptionErrIsDisplayed(){
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(descriptionErrorMsg));
    }

}
