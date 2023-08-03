package com.arsingenii;

import org.junit.jupiter.api.Test;

public class CalculatingChangeFlow {

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
}
