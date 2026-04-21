package base;

import org.openqa.selenium.WebDriver;
import util.AllFunctionalities;

public class baseclass {

    protected WebDriver driver;
    protected AllFunctionalities af;

    public baseclass(WebDriver driver) {
        this.driver = driver;
        this.af = new AllFunctionalities(driver);
    }
}