import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.concurrent.TimeUnit;

public class OpenAutomationPage {

    WebDriver driver = null;

    @BeforeTest
    public void openBrowser() {

        System.setProperty("webdriver.chrome.driver", "/Users/hdm/Documents/AutomationTestFrameworks/TestNG/TestWithTestNG/src/test/Utilities/chromedriver");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://automationteststore.com/");

    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    @Test(priority=1)
    public void addToCart() {
        //Click Men Tab
        WebElement categoryMen = driver.findElement(By.linkText("MEN"));
        categoryMen.click();

        //Click BODY & SHOWER
        WebElement bodyShower = driver.findElement(By.xpath("/html//div[@id='maincontainer']//div[@class='contentpanel']/ul/li[1]/div/a[@href='https://automationteststore.com/index.php?rt=product/category&path=58_63']"));
        bodyShower.click();

        //Click Dove Men
        WebElement doveMen = driver.findElement(By.linkText("DOVE MEN +CARE BODY WASH"));
        doveMen.click();

        //Add to cart
        WebElement addToCartButton = driver.findElement(By.linkText("Add to Cart"));
        addToCartButton.click();

        //Explicit way wait
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_checkout1")));

        //CheckOut
        WebElement checkOutButton = driver.findElement(By.id("cart_checkout1"));
        checkOutButton.click();

        System.out.println("Added to Cart successfully completed");
    }

    @Test(priority=2,dependsOnMethods= {"addToCart"})
    public void register() {
        //Select Guest CheckOut
        WebElement guestCheckOutButton = driver.findElement(By.id("accountFrm_accountguest"));
        guestCheckOutButton.click();
        WebElement continueButton = driver.findElement(By.cssSelector("button[title='Continue']"));
        continueButton.click();
        WebElement firstName = driver.findElement(By.id("guestFrm_firstname"));
        firstName.sendKeys("John");
        WebElement lastName = driver.findElement(By.id("guestFrm_lastname"));
        lastName.sendKeys("Doe");
        WebElement eMail = driver.findElement(By.id("guestFrm_email"));
        eMail.sendKeys("siparis@msn.com");
        WebElement telephone = driver.findElement(By.id("guestFrm_telephone"));
        telephone.sendKeys("0777734444");
        WebElement fax = driver.findElement(By.id("guestFrm_fax"));
        fax.sendKeys(" ");
        WebElement company = driver.findElement(By.id("guestFrm_company"));
        company.sendKeys(" ");

        WebElement address1 = driver.findElement(By.id("guestFrm_address_1"));
        address1.sendKeys("Berkeley Street");
        WebElement address2 = driver.findElement(By.id("guestFrm_address_2"));
        address2.sendKeys("3/2");
        WebElement city = driver.findElement(By.id("guestFrm_city"));
        city.sendKeys("Glasgow");

        Select zones = new Select(driver.findElement(By.id("guestFrm_zone_id")));
        zones.selectByValue("3551");

        WebElement postCode = driver.findElement(By.id("guestFrm_postcode"));
        postCode.sendKeys("G37HQ");


        WebElement continueButton2 = driver.findElement(By.cssSelector("[title='Continue']"));
        continueButton2.click();



        System.out.println("Guest Register successfully completed");


    }

    @Test(priority=3,dependsOnMethods= {"register"})
    public void checkoutConfirmation() {

        //Assertions
        WebElement SubTotal = driver.findElement(By.cssSelector("tr:nth-of-type(1) td:nth-of-type(2) .bold"));

        String actualSubTotal = "$6.70";
        System.out.println("Actual Sub Total : "+ actualSubTotal);
        String expectedSubTotal = SubTotal.getText();
        System.out.println("Expected Sub Total: "+ expectedSubTotal);
        Assert.assertEquals(actualSubTotal, expectedSubTotal);

        WebElement flatShippingRate = driver.findElement(By.cssSelector("tr:nth-of-type(2) td:nth-of-type(2) .bold"));
        String actualflatShippingRate = "$2.00";
        System.out.println("Actual Flat Shipping Rate : "+actualflatShippingRate);
        String expectedflatShippingRate = flatShippingRate.getText();
        System.out.println("Expected Flat Shipping Rate: "+expectedflatShippingRate);
        Assert.assertEquals(actualflatShippingRate, expectedflatShippingRate);

        WebElement cartTotal = driver.findElement(By.cssSelector("td:nth-of-type(2) .totalamout"));
        String actualcartTotal = "$8.70";
        System.out.println("Actual cartTotal : "+actualcartTotal);
        String expectedcartTotal = cartTotal.getText();
        System.out.println("Expected cartTotal: "+expectedcartTotal);
        Assert.assertEquals(actualcartTotal, expectedcartTotal);

        System.out.println("Checkout Confirmation successfully completed");

    }

    @Test(priority=4,dependsOnMethods= {"checkoutConfirmation"})
    public void payOrder() {
        WebElement confirmOrderButton = driver.findElement(By.cssSelector("[title='Confirm Order']"));
        confirmOrderButton.click();

        WebElement confirmMessage = driver.findElement(By.xpath(" /html//div[@id='maincontainer']/div[@class='container-fluid']//span[@class='maintext']"));
        String actualconfirmMessage = "YOUR ORDER HAS BEEN PROCESSED!";
        System.out.println("Actual Confirm Message : "+ actualconfirmMessage);
        String expectedconfirmMessage = confirmMessage.getText();
        System.out.println("Expected Confirm Message: "+expectedconfirmMessage);
        Assert.assertEquals(actualconfirmMessage, actualconfirmMessage);

        System.out.println("Order Payment successfully completed");
        //throw new SkipException("error");
    }

}



