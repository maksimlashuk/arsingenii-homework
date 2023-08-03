package com.arsingenii;

import jdk.jfr.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigDecimal;

import static com.arsingenii.VendingMachineUtils.getElementByTestId;
import static com.arsingenii.VendingMachineUtils.parseAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CancelBuyingTest {

    /**
     * Test will fail as there is a bug in the application.
     */
    @ParameterizedTest
    @CsvSource({"5, 5, 0, 0, 35.00"
    })
    public void cancelBuying(String input5sCount, String input2sCount, String input1sCount, String input10CentsCount, BigDecimal expectedTotalAmount) {
        WebDriver driver = new ChromeDriver();
        driver.get("file:///C:/Users/Darja.ORKLA-DARJA/Documents/vending-machine/index.html");

        try {

            getElementByTestId(driver, "fiveEuro").sendKeys(input5sCount);
            getElementByTestId(driver, "twoEuro").sendKeys(input2sCount);
            getElementByTestId(driver, "oneEuro").sendKeys(input1sCount);
            getElementByTestId(driver, "tenCents").sendKeys(input10CentsCount);

            BigDecimal totalAmount = new BigDecimal(driver.findElement(By.id("Total")).getText());
            assertEquals(expectedTotalAmount, totalAmount, "Wrong total amount");
            System.out.println("Total amount is valid");

            driver.findElement(By.id("cancel")).click();

            String message = getElementByTestId(driver, "message").getText();
            BigDecimal change = parseAmount(message);
            assertEquals(expectedTotalAmount, change, "Wrong change");
            System.out.println("Change is valid");

            double finalTotalAmount = Double.parseDouble(driver.findElement(By.id("Total")).getText());
            assertEquals(Double.parseDouble("0"), finalTotalAmount, "Total Amount is NOT changed");

        } finally {
            driver.quit();
        }
    }
}
