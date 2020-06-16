import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.DriverManagerType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestAdvisoryDemoTests
{

    private String appUrl = "https://coealmtestadvisorydemo-yfpbuktivt.dispatcher.hana.ondemand.com/index.html?hc_reset";
    private final static String PAGE_TITLE = "CoE ALM Test Advisory Demo Application";
    private WebDriver driver;
    private String productName = "Chai";
    private String unitPrice = "18.00";


    @BeforeMethod
//    start driver
//    define implicit wait time
    public void setUp() {
        ChromeDriverManager.getInstance(DriverManagerType.CHROME).setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
//    open test advisory app
//    check page title
    private void openApp () {

//        WebDriver driver = new ChromeDriver();
        driver.get(appUrl);
        Assert.assertEquals(driver.getTitle(), PAGE_TITLE);
        String pageTitle = driver.findElement(By.id("__title0-inner")).getText();
        Assert.assertEquals(pageTitle, "Coe ALM Test Advisory Demo");
    }

    @Test
//    open test advisory app
//    search for product name
//    check its unit price
    public void checkProductPrice () {

        openApp();
        findProduct(productName, unitPrice);

    }


    @AfterMethod
//    shutdown driver
    public void tearDown() {

        if (driver!=null) {
            driver.quit();
        }
    }

    private void findProduct (String productName, String unitPrice) {
        WebElement searchEditBox = driver.findElement(By.xpath("//*[@placeholder='Search']"));
        searchEditBox.sendKeys(productName);
        driver.findElement(By.xpath("//*[@id='__component0---worklist--searchField-search']")).click();
        List<WebElement> foundProducts = driver.findElements(By.xpath("//*[@class='sapMObjectIdentifier']"));
        List<WebElement> foundPrices = driver.findElements(By.xpath("//*[@class='sapMObjectNumberText']"));
//        int requiredId = 0;
        if (foundProducts.size() > 0) {

//            for (WebElement we : foundProducts) {
//                System.out.println(we.getText());
//            }
            for (int i=0; i < foundProducts.size(); i++) {
                if (foundProducts.get(i).getText().equals(productName)) {
                    Assert.assertEquals(foundPrices.get(i).getText(), unitPrice);
                    System.out.println("Price for product " + foundProducts.get(i).getText() + " successfully checked and the price is " + foundPrices.get(i).getText());
                }
            }
        } else Assert.fail("No product with name " + productName + " found");
    }

}