package auto_test;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.LoginPage;

public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    // LOG-001
    @Test
    public void testDisplayLoginPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    }

    // LOG-002
    @Test
    public void testLoginSuccess() {
        loginPage.login("admin", "admin123");
        Assert.assertTrue(driver.getCurrentUrl().contains("home"));    
    }

    // LOG-003
    @Test
    public void testWrongPassword() {
        loginPage.login("admin", "abc");
        String errorText = driver.findElement(By.id("message")).getText();

        Assert.assertEquals(errorText, "Sai tên đăng nhập hoặc mật khẩu!");
    }

}

    // LOG-004
    // @Test
    // public void testUserNotExist() {
    //     login("test", "123", false);
    //     String errorText = driver.findElement(By.id("message")).getText();  
    //     Assert.assertEquals(errorText, "Sai tên đăng nhập hoặc mật khẩu!");
    // }

    // LOG-005
    // @Test
    // public void testEmptyUsername() {
    //     login("", "123", false);
    //     Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    // }

    // LOG-006
    // @Test
    // public void testEmptyPassword() {
    //     login("admin", "", false);
    //     Assert.assertTrue(driver.getCurrentUrl().contains("login"));
    // }

    
    // LOG-007
    // @Test
    // public void testRememberMe() {
    //     loginPage.loginWithRemember("admin", "admin123");
    //     Cookie cookie = driver.manage().getCookieNamed("user");
    //     Assert.assertNotNull(cookie);
    // }

    // LOG-008
    // @Test
    // public void testSessionCreated() {
    //     loginPage.login("admin", "admin123");
    //     Cookie session = driver.manage().getCookieNamed("JSESSIONID");
    //     Assert.assertNotNull(session);
    // }


    
