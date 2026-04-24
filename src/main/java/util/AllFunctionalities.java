package util;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class AllFunctionalities {

    private static WebDriver driver;
    private static Properties prop = new Properties();
    private final WebDriverWait wait;

    public AllFunctionalities(WebDriver driver) {
        AllFunctionalities.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public static void launchBrowser() {
        if (driver == null) {
            String browser = getConfig("browser");

            if (browser == null || browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--remote-allow-origins=*");

                driver = new ChromeDriver(options);
            } else {
                throw new RuntimeException("Only chrome browser is configured currently.");
            }
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    public void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public void selectByVisibleText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
//Dhana changes 
    public static Object[][] getData(String sheetName) {
        try {
        	InputStream fis = AllFunctionalities.class
        	        .getClassLoader()
        	        .getResourceAsStream("testdata/Booking.xlsx");

        	if (fis == null) {
        	    throw new RuntimeException("❌ Excel file NOT FOUND in resources!");
        	}

        	Workbook wb = WorkbookFactory.create(fis);  // ✅ CREATE FIRST

        	

        	Sheet sheet = wb.getSheet(sheetName.trim()); // ✅ trim added

        	if (sheet == null) {
        	    throw new RuntimeException("❌ Sheet NOT FOUND: " + sheetName);
        	}

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getPhysicalNumberOfCells();

            Object[][] data = new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
                }
            }

            wb.close();
            fis.close();

            return data;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getConfig(String key) {
        try {
            if (prop.isEmpty()) {
                FileInputStream fis =
                        new FileInputStream("src/main/resources/config.properties");

                prop.load(fis);
                fis.close();
            }

            return prop.getProperty(key);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    
    //stays-v added
    public WebElement waitForClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
}
    
}

