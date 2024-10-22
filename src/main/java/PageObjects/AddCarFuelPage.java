package PageObjects;

import Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddCarFuelPage extends BasePage {
    public AddCarFuelPage(WebDriver driver) {
        super(driver);
    }

    private By carNumberField = By.xpath("//*[@id='root']/div/form/div[1]/input");
    private By fuelAmountField = By.xpath("//*[@id='root']/div/form/div[2]/input");
    private By fuelCostField = By.xpath("//*[@id='root']/div/form/div[3]/input");
    private By fuelTypeField = By.xpath("//*[@id='root']/div/form/div[4]/input");
    private By dateTimeField = By.xpath("//*[@id='root']/div/form/div[5]/input");
    private By companyIdField = By.xpath("//*[@id='root']/div/form/div[6]/input");
    private By addButton = By.xpath("//*[@id='root']/div/form/button");

    private By dateInTable = By.xpath("//*[@id='root']/div/table/tbody/tr[1]/td[4]");
    private By deleteButtonForRecentRecord = By.xpath("//button[contains(text(),'Delete')]");

    private By tableLocator = By.className("table-striped");



    public boolean isTableEmpty() {
        WebElement table = driver.findElement(tableLocator);
        int rowCount = table.findElements(By.tagName("tr")).size();
        return rowCount == 1;
    }
    public void enterCarNumber(String carNumber) {
        driver.findElement(carNumberField).sendKeys(carNumber);
    }

    public void enterFuelAmount(String fuelAmount) {
        driver.findElement(fuelAmountField).sendKeys(fuelAmount);
    }

    public void enterFuelCost(String fuelCost) {
        driver.findElement(fuelCostField).sendKeys(fuelCost);
    }

    public void enterFuelType(String fuelType) {
        driver.findElement(fuelTypeField).sendKeys(fuelType);
    }

    public void enterDateTime(String dateTime) {
        driver.findElement(dateTimeField).sendKeys(dateTime);
    }

    public void enterCompanyId(String companyId) {
        driver.findElement(companyIdField).sendKeys(companyId);
    }

    public void clickAddButton() {
        driver.findElement(addButton).click();
    }


    public String getDateFromTable() {
        return driver.findElement(dateInTable).getText();
    }

    public void deleteMostRecentRecord() {
       driver.findElement(deleteButtonForRecentRecord).click();
    }

}
