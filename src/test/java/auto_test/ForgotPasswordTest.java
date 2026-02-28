package auto_test;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.ForgotPasswordPage;

public class ForgotPasswordTest extends BaseTest {
    ForgotPasswordPage forgotPage;

    @BeforeMethod
    public void setup() {
        forgotPage = new ForgotPasswordPage(driver);
        forgotPage.open();
    }

    @Test
    public void FGP_004_UsernameNotExists() {
        forgotPage.forgotPassword("abcde", "any@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Tài khoản không tồn tại!");
    }

    @Test
    public void FGP_005_EmailWrong() {
        forgotPage.forgotPassword("test1", "wrongemail@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Email không khớp với tài khoản!");
    }

    @Test
    public void FGP_006_Success() {
        // Đảm bảo username test1 và email test@gmail.com có trong DB
        forgotPage.forgotPassword("test1", "test@gmail.com");
        Assert.assertEquals(forgotPage.getMessage(), "Mật khẩu đã được gửi về email!");
    }

}