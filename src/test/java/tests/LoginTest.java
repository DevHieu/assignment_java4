package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get("http://localhost:9090/login");
    }

    private void login(String username, String password, boolean remember) {
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("password")).clear();

        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);

        if (remember) {
            driver.findElement(By.name("remember")).click();
        }

        driver.findElement(By.id("btnLogin")).click();
    }

    // LOG-001
    @Test
    public void testDisplayLoginPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // LOG-002
    @Test
    public void testLoginSuccess() {
        login("admin", "admin123", false);
        Assert.assertTrue(driver.getCurrentUrl().contains("home"));
    }
    

    // LOG-003
    @Test
    public void testWrongPassword() {
        login("admin", "abc", false);
        String errorText = driver.findElement(By.id("message")).getText();

        Assert.assertEquals(errorText, "Sai tên đăng nhập hoặc mật khẩu!");
    }

    // LOG-004
    @Test
    public void testUserNotExist() {
        login("test", "123", false);
        String errorText = driver.findElement(By.id("message")).getText();
        
        Assert.assertEquals(errorText, "Sai tên đăng nhập hoặc mật khẩu!");
    }

    // LOG-005
    @Test
    public void testEmptyUsername() {
        login(null, "123", false);
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // LOG-006
    @Test
    public void testEmptyPassword() {
        login("admin", null, false);
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // LOG-007
    @Test
    public void testRememberMe() {
        login("admin", "admin123", true);

        Cookie cookie = driver.manage().getCookieNamed("user");
        Assert.assertNotNull(cookie);
    }

    // LOG-008
    @Test
    public void testSessionCreated() {
        login("admin", "admin123", false);

        Cookie session = driver.manage().getCookieNamed("JSESSIONID");
        Assert.assertNotNull(session);
    }
}