package auto_test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import page.RegisterPage;

public class RegisterTest extends BaseTest {

    @BeforeMethod
    public void navigateToRegisterPage() {
        driver.get("http://localhost:9090/register");
    }

    @Test
    public void testAllEmptyFields() {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.open();
        registerPage.register("", "", "", "", "");

        String alertText = registerPage.getAlertMessage();

        Assert.assertTrue(
                alertText.contains("Vui lòng điền đầy đủ thông tin!"));
    }

    @Test
    public void testWrongPasswordRepeat() {
        RegisterPage registerPage = new RegisterPage(driver);

        registerPage.open();
        registerPage.register("Nguyen Van Test", "test1", "test@gmail.com", "123", "456");

        String alertText = registerPage.getAlertMessage();

        Assert.assertTrue(
                alertText.contains("Mật khẩu xác nhận không khớp!"));
    }

    @Test
    public void testRegisterSuccess() {
        RegisterPage registerPage = new RegisterPage(driver);

        String username = "user" + System.currentTimeMillis();
        String email = "user" + System.currentTimeMillis() + "@test.com";

        registerPage.open();
        registerPage.register("Nguyen Van Test", username, email, "123", "123");

        String alertText = registerPage.getAlertMessage();

        Assert.assertTrue(
                alertText.contains("Đăng ký thành công"));
    }
}
