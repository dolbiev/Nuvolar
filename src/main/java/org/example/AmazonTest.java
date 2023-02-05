package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AmazonTest {
    @Test
    public static void main(String[] args) {

        int quantityMan = 2;
        int quantityWoman = 1;

        //Before test
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        WebDriver driver = new ChromeDriver(chromeOptions);

        //Navigate to Url
        driver.get("https://www.amazon.com");
        //Expand to full screen
        driver.manage().window().maximize();
        //Clear search field
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        //Find "hats for man"
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("hats for men");
        driver.findElement(By.id("nav-search-submit-button")).click();
        //Select first hat
        driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']")).click();
        //Set quantity and add to cart
        try {
            driver.findElement(By.xpath("//span[@class='a-dropdown-label']")).click();
            driver.findElement(By.xpath("//a[@class='a-dropdown-link' and normalize-space(text())='" + quantityMan + "']")).click();
            driver.findElement(By.xpath("//div[@class='a-button-stack']//input[@id='add-to-cart-button']")).click();

        } catch (NoSuchElementException e) {
            driver.findElement(By.id("dropdown_selected_size_name")).click();
            driver.findElement(By.xpath("//span[@class='a-dropdown-prompt' and normalize-space(text())='Medium-Large']")).click();
            driver.findElement(By.id("exportAlternativeTriggerButton-announce")).click();
            driver.findElement(By.xpath("//span[normalize-space(text())='Add to Cart']")).click();
        }
        driver.findElement(By.id("nav-cart-count")).click();
        String priceDollarMan = driver.findElement(By.xpath("//div[@class='a-section a-spacing-none aok-align-center']//span[@class='a-price-whole']")).getText();
        String priceCentMan = driver.findElement(By.xpath("//div[@class='a-section a-spacing-none aok-align-center']//span[@class='a-price-fraction']")).getText();

        //Assert prices
        String priceTextMan = priceDollarMan + "." + priceCentMan;
        Float priceMan = Float.valueOf(priceTextMan).floatValue();
        float totalPriceMan = quantityMan * priceMan;
        String totalPriceManFromCart = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText();
        String totalPriceManWithS = "$" + String.valueOf(totalPriceMan);
        System.out.println(totalPriceManFromCart.equals(totalPriceManWithS));

        //Clear search field
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        //Find "hats for man"
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("hats for women");
        driver.findElement(By.id("nav-search-submit-button")).click();
        //Select first hat
        driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']")).click();
        //Set quantity and add to cart
        try {
            driver.findElement(By.xpath("//span[@class='a-dropdown-label']")).click();
            driver.findElement(By.xpath("//a[@class='a-dropdown-link' and normalize-space(text())='" + quantityWoman + "']")).click();
        } catch (NoSuchElementException e) {
            driver.findElement(By.id("twotabsearchtextbox")).clear();
            driver.findElement(By.id("twotabsearchtextbox")).sendKeys("hats for women");
            driver.findElement(By.id("nav-search-submit-button")).click();
            driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']")).click();
            return;
        }
        String priceDollarWoman = driver.findElement(By.xpath("//div[@class='a-section a-spacing-none aok-align-center']//span[@class='a-price-whole']")).getText();
        String priceCentWoman = driver.findElement(By.xpath("//div[@class='a-section a-spacing-none aok-align-center']//span[@class='a-price-fraction']")).getText();
        driver.findElement(By.xpath("//div[@class='a-button-stack']//input[@id='add-to-cart-button']")).click();
        driver.findElement(By.id("nav-cart-count")).click();
        //Assert prices
        String priceTextWoman = priceDollarWoman + "." + priceCentWoman;
        Float priceWoman = Float.valueOf(priceTextWoman).floatValue();
        float totalPriceWoman = quantityWoman * priceWoman + totalPriceMan;
        String totalPriceWomanFromCart = driver.findElement(By.xpath("//span[@id='sc-subtotal-amount-activecart']/span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText();
        String totalPriceWomanWithS = "$" + String.valueOf(totalPriceWoman);
        System.out.println(totalPriceWomanFromCart.equals(totalPriceWomanWithS));

        //Back to cart
        driver.findElement(By.id("nav-cart-count")).click();

    }
}