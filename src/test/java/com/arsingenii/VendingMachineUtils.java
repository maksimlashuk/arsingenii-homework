package com.arsingenii;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VendingMachineUtils {

    public static WebElement getElementByTestId(WebDriver driver, String testId) {
        return driver.findElement(By.cssSelector("[data-testid='" + testId + "']"));
    }

    public static BigDecimal parseAmount(String input) {
        Pattern pattern = Pattern.compile("(?<=€)\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String amountStr = matcher.group();
            return new BigDecimal(amountStr);
        } else {
            throw new IllegalArgumentException("No amount found in the input string.");
        }
    }
}
