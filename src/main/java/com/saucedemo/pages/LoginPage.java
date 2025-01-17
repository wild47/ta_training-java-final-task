package com.saucedemo.pages;

import com.saucedemo.models.User;
import com.saucedemo.tests.LoginTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @FindBy(xpath = "//input[@data-test='username']")
    private WebElement usernameArea;

    @FindBy(xpath = "//input[@data-test='password']")
    private WebElement passwordArea;

    @FindBy(xpath = "//input[@id='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@data-test='error']")
    private List<WebElement> errorMassage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public LoginPage enterLoginFields(User user) {
        logger.info("Entering login fields for user: {}", user);

        enterUsername(user.getUsername());
        enterPassword(user.getPassword());
        return this;
    }


    public LoginPage enterUsername(String username) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(usernameArea));

        if(username != null) {
            usernameArea.sendKeys(username);
            logger.debug("Entered username: {}", username);
        } else {
            String password = passwordArea.getAttribute("value");;
            driver.navigate().refresh();
            usernameArea.clear();
            logger.debug("Cleared username field");
            if(!password.isEmpty()) {
                enterPassword(password);
            }
        }
        return this;
    }


    public LoginPage enterPassword(String password) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(passwordArea));

        if(password != null) {
            passwordArea.sendKeys(password);
            logger.debug("Entered password: {}", password);
        } else {
            String username = usernameArea.getAttribute("value");
            driver.navigate().refresh();
            passwordArea.clear();
            logger.debug("Cleared password field");
            if(!username.isEmpty()) {
                enterUsername(username);
            }
        }
        return this;
    }


    public LoginButtonClickResult clickLoginButton() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOf(loginButton));

        loginButton.click();
        logger.info("Clicked login button");

        if(driver.getCurrentUrl().equals("https://www.saucedemo.com/")) {
            return new LoginButtonClickResult(this);
        } else {
            return new LoginButtonClickResult(new ShopPage(driver));
        }
    }


    public String getErrorMassageText() {
        if(!errorMassage.isEmpty()) {
            String fullText = errorMassage.get(0).getText();
            return fullText.substring(14);
        } else {
            return "";
        }
    }


}
