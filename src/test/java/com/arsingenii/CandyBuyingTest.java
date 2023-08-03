package com.arsingenii;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.math.BigDecimal;

import static com.arsingenii.VendingMachineUtils.getElementByTestId;
import static com.arsingenii.VendingMachineUtils.parseAmount;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandyBuyingTest {

    /**
     * TC1 will fail as there is a bug in the application.
     */

    @ParameterizedTest
    @CsvSource({"0, 0, 2, 0, 2.00",
            "0, 0, 2, 1, 2.10",
            "1, 1, 1, 1, 8.10"
    })
    public void buyCandy(String input5sCount, String input2sCount, String input1sCount, String input10CentsCount, BigDecimal expectedTotalAmount) {
        WebDriver driver = new ChromeDriver();
        driver.get("file:///C:/Users/Darja.ORKLA-DARJA/Documents/vending-machine/index.html");

        try {

            BigDecimal productPrice = new BigDecimal(2.00);
            BigDecimal expectedChange = expectedTotalAmount.subtract(productPrice);

            getElementByTestId(driver, "fiveEuro").sendKeys(input5sCount);
            getElementByTestId(driver, "twoEuro").sendKeys(input2sCount);
            getElementByTestId(driver, "oneEuro").sendKeys(input1sCount);
            getElementByTestId(driver, "tenCents").sendKeys(input10CentsCount);

            BigDecimal totalAmount = new BigDecimal(driver.findElement(By.id("Total")).getText());
            assertEquals(expectedTotalAmount, totalAmount, "Wrong total amount");
            System.out.println("Total amount is valid");

            getElementByTestId(driver, "twix").click();

            String message = getElementByTestId(driver, "message").getText();
            BigDecimal change = parseAmount(message);
            assertEquals(expectedChange, change, "Wrong change");
            System.out.println("Change is valid");

            double finalTotalAmount = Double.parseDouble(driver.findElement(By.id("Total")).getText());
            assertEquals(Double.parseDouble("0"), finalTotalAmount, "Total Amount is NOT changed");

        } finally {
            driver.quit();
        }
    }
}

