package TestCases;

import Config.ConfigUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetroTest extends BaseTest {

    @Test
    public void AddNewRecord() {

        int carNumber = faker.number().numberBetween(1, 1000);
        int companyId = faker.number().randomDigit();
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        addCarFuelPage.enterCarNumber(String.valueOf(carNumber));
        addCarFuelPage.enterFuelAmount(String.valueOf(carNumber));
        addCarFuelPage.enterFuelCost(String.valueOf(carNumber));
        addCarFuelPage.enterFuelType(faker.name().name());
        addCarFuelPage.enterDateTime("2024-10-22T18:32");
        addCarFuelPage.enterCompanyId(String.valueOf(companyId));
        addCarFuelPage.clickAddButton();

        // Assert the date exists in the table
        String date = addCarFuelPage.getDateFromTable();
        Assert.assertEquals(date, "41022-02-02T18:32");
    }

    @Test
    public void DeleteRecord() throws InterruptedException {

        int carNumber = faker.number().numberBetween(1, 1000);
        int companyId = faker.number().randomDigit();
        driver.get(ConfigUtils.getInstance().getBaseUrl());
        addCarFuelPage.enterCarNumber(String.valueOf(carNumber));
        addCarFuelPage.enterFuelAmount(String.valueOf(carNumber));
        addCarFuelPage.enterFuelCost(String.valueOf(carNumber));
        addCarFuelPage.enterFuelType(faker.name().name());
        addCarFuelPage.enterDateTime("2024-10-22T18:32");
        addCarFuelPage.enterCompanyId(String.valueOf(companyId));
        addCarFuelPage.clickAddButton();
        // Assert the date exists in the table
        String date = addCarFuelPage.getDateFromTable();
        Assert.assertEquals(date, "41022-02-02T18:32");
        addCarFuelPage.deleteMostRecentRecord();
        Assert.assertTrue(addCarFuelPage.isTableEmpty(), "The table is not empty.");


    }

    @Test

    public void AddwithInvaidData() {

        driver.get(ConfigUtils.getInstance().getBaseUrl());
        addCarFuelPage.enterCarNumber("&&%%%??   jdklf");
        addCarFuelPage.enterFuelAmount("abc");
        addCarFuelPage.enterFuelCost("????0000cfgg");
        addCarFuelPage.enterFuelType(faker.name().name());
        addCarFuelPage.enterDateTime("2024-10-22T18:32");
        addCarFuelPage.enterCompanyId("qwqqcw  >>>>>");
        addCarFuelPage.clickAddButton();
        Assert.assertTrue(addCarFuelPage.isTableEmpty(), "The table is not empty.");
    }
}

