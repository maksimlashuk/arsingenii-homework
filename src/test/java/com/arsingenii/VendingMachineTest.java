package com.arsingenii;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineTest {

    @Test
    public void calculateChange() {
        double insertedSum = 5.19;
        double productPrice = 2.00;

        double change = insertedSum - productPrice;

        int count50s = (int) (change / 0.50);
        int count20s = (int) ((change - count50s * 0.50)/ 0.20);
        int count10s = (int) ((change - (count50s * 0.50 + count20s * 0.20)) / 0.10);
        int count1s = (int) ((change - (count50s * 0.50 + count20s * 0.20 + count10s * 0.10)) / 0.01);

        System.out.println("The change consists of " + count50s + " 50s coins " + count20s + " 20s coins " + count10s + " 10s coins " + count1s + " 1s coins." );
    }

    @Test
    public void buyTwix() {
        WebDriver driver = new ChromeDriver();
        driver.get("file:///C:/Users/Darja.ORKLA-DARJA/Documents/vending-machine/index.html");

        try {
            String input5sCount = "5",
                    input2sCount = "5",
                    input1sCount = "",
                    input10CentsCount = "";
            double expectedTotalAmount = 35,
                    expectedChange = expectedTotalAmount - 2;

            getElementByTestId(driver, "fiveEuro").sendKeys(input5sCount);
            getElementByTestId(driver, "twoEuro").sendKeys(input2sCount);
            getElementByTestId(driver, "oneEuro").sendKeys(input1sCount);
            getElementByTestId(driver, "tenCents").sendKeys(input10CentsCount);

            double totalAmount = Double.parseDouble(driver.findElement(By.id("Total")).getText());//TODO: exception gali buti
            assertEquals(expectedTotalAmount, totalAmount, "Wrong total amount");
            System.out.println("Total amount is valid");

            getElementByTestId(driver, "twix").click();

            String message = getElementByTestId(driver, "message").getText();
            double change = parseAmount(message);
            assertEquals(expectedChange, change, "Wrong change");
            System.out.println("Change is valid");

//            String totalAmountText = driver.findElement(By.id("Total")).getText();
//            double totalAmount = Double.parseDouble(totalAmountText);
            assertEquals("0", totalAmount, "Total Amount is NOT changed");

        } finally {
            driver.quit();
        }
    }

    private static WebElement getElementByTestId(WebDriver driver, String testId) {
        return driver.findElement(By.cssSelector("[data-testid='" + testId + "']"));
    }

    private static double parseAmount(String input) {
        Pattern pattern = Pattern.compile("(?<=€)\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String amountStr = matcher.group();
            return Double.parseDouble(amountStr);
        } else {
            throw new IllegalArgumentException("No amount found in the input string.");
        }
    }
}

