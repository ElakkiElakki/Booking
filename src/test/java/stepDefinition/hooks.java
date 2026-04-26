package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.pages;
import util.AllFunctionalities;

import java.time.Duration;
import java.util.List;

public class hooks {

    private static final int MANUAL_OTP_WAIT_SECONDS = 120;
    private static final int DEFAULT_WAIT_SECONDS = 15;
    
    //added for single login
    private static boolean alreadyLoggedIn = false;

    @Before(order = 1)
    public void setUp() {
        AllFunctionalities.launchBrowser();
        pages.loadAllPages(AllFunctionalities.getDriver());
    }

    @Before(value = "@Login", order = 2)
    public void loginToApplication() {
//  added for single login
    	 if (alreadyLoggedIn) {
    	        System.out.println("Already logged in. Skipping login.");
    	        return;
    	    }
//end
    	 
    	 WebDriver driver = AllFunctionalities.getDriver();
        AllFunctionalities af = new AllFunctionalities(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));

        af.openUrl(AllFunctionalities.getConfig("baseUrl"));
        System.out.println("Website opened for login: " + AllFunctionalities.getConfig("baseUrl"));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        System.out.println("Current page title: " + driver.getTitle());
        System.out.println("Current page URL: " + driver.getCurrentUrl());

        handleCookies(driver);
        clickSignIn(driver, af);
        handleCookies(driver);

        enterEmailAndContinue(driver, af, AllFunctionalities.getConfig("email"));
        handleCookies(driver);

        waitForManualOtpAndValidateLogin(driver, af);

        if (!isLoggedInStateVisible(driver)) {
            throw new RuntimeException("Login failed. User account state is not visible.");
        }
        alreadyLoggedIn = true;
        System.out.println("Login completed successfully from Hooks.");
    }
    @Before(value = "@Login", order = 3)
    public void openFreshPageBeforeScenario() {
        if (!alreadyLoggedIn) return;

        WebDriver driver = AllFunctionalities.getDriver();

        try {
            driver.get("https://www.booking.com/flights/");
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            handleCookies(driver);

            System.out.println("Fresh flights page opened. Login kept.");
        } catch (Exception e) {
            System.out.println("Fresh page open failed: " + e.getMessage());
        }
    }
    private void handleCookies(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

            try {
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                        By.xpath("//iframe[contains(@id,'sp_message_iframe')]")
                ));
            } catch (Exception ignored) {
            }

            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Accept') or contains(.,'Agree') or contains(.,'I agree') or contains(.,'Accept all')]")
            ));

            try {
                btn.click();
            } catch (Exception e) {
                try {
                    ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", btn);
                } catch (Exception ignored) {
                }
            }

            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {
            }

            System.out.println("Cookie handled");

        } catch (Exception e) {
            System.out.println("No cookie popup");

            try {
                driver.switchTo().defaultContent();
            } catch (Exception ignored) {
            }
        }
    }

    private void clickSignIn(WebDriver driver, AllFunctionalities af) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));

        By[] signInLocators = new By[] {
                By.cssSelector("a[data-testid='header-sign-in-button']"),
                By.xpath("//a[@data-testid='header-sign-in-button']"),
                By.xpath("//a[.//span[normalize-space()='Sign in']]"),
                By.xpath("//button[.//span[normalize-space()='Sign in']]"),
                By.xpath("//a[contains(normalize-space(),'Sign in')]"),
                By.xpath("//button[contains(normalize-space(),'Sign in')]"),
                By.xpath("//*[self::a or self::button][contains(normalize-space(),'Sign in')]")
        };

        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(signInLocators[0]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[1]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[2]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[3]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[4]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[5]),
                    ExpectedConditions.presenceOfElementLocated(signInLocators[6])
            ));
        } catch (Exception e) {
            throw new RuntimeException("Sign in button did not appear on the page.");
        }

        for (By locator : signInLocators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        try {
                            af.scrollToElement(element);
                        } catch (Exception ignored) {
                        }

                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                        } catch (Exception e) {
                            af.jsClick(element);
                        }

                        System.out.println("Clicked Sign in button using locator: " + locator);
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Sign in button not found.");
    }

    private void enterEmailAndContinue(WebDriver driver, AllFunctionalities af, String email) {
        WebElement emailBox = findVisibleEmailField(driver);

        if (emailBox == null) {
            throw new RuntimeException("Email field not found on login page.");
        }

        try {
            af.scrollToElement(emailBox);
        } catch (Exception ignored) {
        }

        try {
            af.click(emailBox);
        } catch (Exception e) {
            af.jsClick(emailBox);
        }

        emailBox.clear();
        emailBox.sendKeys(email);
        System.out.println("Email entered: " + email);

        clickContinueButton(driver, af);
    }

    private WebElement findVisibleEmailField(WebDriver driver) {
        By[] emailLocators = new By[] {
                By.id("username"),
                By.name("username"),
                By.cssSelector("input[type='email']"),
                By.cssSelector("input[name='username']"),
                By.cssSelector("input[autocomplete='username']"),
                By.xpath("//input[contains(@type,'email')]"),
                By.xpath("//input[contains(@name,'username')]"),
                By.xpath("//input[contains(@autocomplete,'username')]")
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));

        for (By locator : emailLocators) {
            try {
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        System.out.println("Email field found using: " + locator);
                        return element;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private void clickContinueButton(WebDriver driver, AllFunctionalities af) {
        By[] buttonLocators = new By[] {
                By.xpath("//button[@type='submit']"),
                By.xpath("//button[contains(normalize-space(),'Continue')]"),
                By.xpath("//button[contains(normalize-space(),'Verify')]"),
                By.xpath("//button[contains(normalize-space(),'Verify email')]"),
                By.xpath("//button[contains(normalize-space(),'Next')]"),
                By.xpath("//span[contains(normalize-space(),'Continue')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Verify')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Verify email')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Next')]/ancestor::button")
        };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));

        for (By locator : buttonLocators) {
            try {
                List<WebElement> buttons = driver.findElements(locator);
                for (WebElement button : buttons) {
                    if (button.isDisplayed()) {
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
                        } catch (Exception e) {
                            af.jsClick(button);
                        }
                        System.out.println("Continue / Verify / Next button clicked.");
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
        }

        throw new RuntimeException("Continue / Verify / Next button not found.");
    }

    private void waitForManualOtpAndValidateLogin(WebDriver driver, AllFunctionalities af) {
        long endTime = System.currentTimeMillis() + (MANUAL_OTP_WAIT_SECONDS * 1000L);

        System.out.println("Please complete OTP manually...");
        System.out.println("Waiting up to " + MANUAL_OTP_WAIT_SECONDS + " seconds");

        while (System.currentTimeMillis() < endTime) {

            handleCookies(driver);

            if (isLoggedInStateVisible(driver)) {
                System.out.println("Login success detected.");
                return;
            }

            clickPostOtpVerifyButtonIfPresent(driver, af);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (isLoggedInStateVisible(driver)) {
                System.out.println("Login success detected after OTP verification click.");
                return;
            }
        }

        throw new RuntimeException("Manual OTP/login was not completed within " + MANUAL_OTP_WAIT_SECONDS + " seconds.");
    }

    private void clickPostOtpVerifyButtonIfPresent(WebDriver driver, AllFunctionalities af) {
        By[] otpButtonLocators = new By[] {
                By.xpath("//button[@type='submit']"),
                By.xpath("//button[contains(normalize-space(),'Verify')]"),
                By.xpath("//button[contains(normalize-space(),'Verify email')]"),
                By.xpath("//button[contains(normalize-space(),'Continue')]"),
                By.xpath("//button[contains(normalize-space(),'Next')]"),
                By.xpath("//span[contains(normalize-space(),'Verify')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Verify email')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Continue')]/ancestor::button"),
                By.xpath("//span[contains(normalize-space(),'Next')]/ancestor::button")
        };

        for (By locator : otpButtonLocators) {
            try {
                List<WebElement> buttons = driver.findElements(locator);
                for (WebElement button : buttons) {
                    if (button.isDisplayed() && button.isEnabled()) {
                        try {
                            af.scrollToElement(button);
                        } catch (Exception ignored) {
                        }

                        try {
                            af.click(button);
                        } catch (Exception e) {
                            af.jsClick(button);
                        }

                        System.out.println("Clicked post-OTP verify button using locator: " + locator);
                        return;
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    private boolean isLoggedInStateVisible(WebDriver driver) {
        try {
            By[] successLocators = new By[] {
                    By.xpath("//button[contains(@aria-label,'account') or @data-testid='header-profile-menu-button']"),
                    By.xpath("//a[contains(@href,'mydashboard') or contains(@href,'account')]"),
                    By.xpath("//img[contains(@alt,'profile') or contains(@src,'avatar')]")
            };

            for (By locator : successLocators) {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @After
    public void afterScenario() {
        System.out.println("Scenario completed. Browser kept for next scenario.");
    }
    //Report start
    
    
    //End
    @AfterAll
    public static void closeAfterAllScenarios() {
        System.out.println("All scenarios completed.");

        try {
            AllFunctionalities.closeBrowser();
        } catch (Exception e) {
            System.out.println("Browser close failed: " + e.getMessage());
        }
    }
}